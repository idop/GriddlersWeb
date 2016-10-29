/**
 * Created by amitaihandler on 10/18/16.
 */
'use strict';

angular.module('Game')

    .controller('GameController',
        ['$scope', '$rootScope', '$location', 'GameService',
            function ($scope, $rootScope, $location, GameService) {
                $scope.gameTitle = ($location.search()).title;
                $scope.PlayerList = [];
                $scope.rowConstraints = [];
                $scope.columnConstraints = [];
                $scope.playerId = null;
                $scope.totalRounds = null;
                $scope.Game = {};
                $scope.currentGameStatus = null;
                $scope.currentPlayerId = null;
                $scope.playerList = [];
                $scope.isGameStarted = false;
                $scope.isGameEnded = false;
                $scope.pageRefrshInterval = 0;
                $scope.boardSquare = 'Black';
                $scope.playerBoard = [[]];
                $scope.moveMap = new Map();
                $scope.isDisabled = true;
                $scope.isDisabled2 = true;
                $scope.alwaysTrue = true;
                $scope.playerType = null;
                $scope.playerName = ($location.search()).playerName;
                function getInitialPageResources() {
                    GameService.getGameInfo($scope.gameTitle, onGetGameInfoInitialSuccess, onGetGameInfoError);
                    GameService.getConstraints($scope.gameTitle, onGetConstraintsInitialSuccess, onGetConstraintsError);
                }

                function getPageResources() {
                    GameService.getGameInfo($scope.gameTitle, onGetGameInfoSuccess, onGetGameInfoError);
                    GameService.getConstraints($scope.gameTitle, onGetConstraintsSuccess, onGetConstraintsError);
                }


                function onGetConstraintsSuccess(response) {
                    $scope.rowConstraints = response["row"];
                    $scope.columnConstraints = response["column"];
                }

                function onGetConstraintsInitialSuccess(response) {
                    onGetConstraintsSuccess(response);
                    $scope.playerBoard = new Array($scope.rowConstraints.length);
                    for (var i = 0; i < $scope.rowConstraints.length; ++i) {
                        $scope.playerBoard[i] = new Array($scope.columnConstraints[0].length);
                        for (var j = 0; j < $scope.columnConstraints[0].length; ++j) {
                            $scope.playerBoard[i][j] = {"color": "Empty", "isSelected": false}
                        }
                    }
                }

                function onGetConstraintsError(response) {
                }

                function onGetGameInfoSuccess(response) {
                    extractGameInfo(response);
                    if ($scope.isGameStarted) {
                        if ($scope.playerId == $scope.currentPlayerId) {
                            if ($scope.playerType == "Computer") {
                                $scope.isDisabled = $scope.isDisabled2 = true;
                                GameService.doPlayerTurn($scope.gameTitle, $scope.playerId, $scope.playerType, {}, onDoComputerTurnSuccess, onDoPlayerTurnError)
                            } else {
                                $scope.isDisabled = $scope.isDisabled2 = false;
                            }
                        } else {
                            $scope.isDisabled = $scope.isDisabled2 = true;
                        }
                    }
                    if ($scope.isGameEnded) {
                        $scope.isDisabled = true;
                        $scope.isDisabled2 = false;
                        clearInterval($scope.pageRefrshInterval);
                    }
                }

                function onDoComputerTurnSuccess() {
                    GameService.getPlayerBoard($scope.gameTitle, $scope.playerId, onGetPlayerBoardSuccess, onGetPlayerBoardError);
                    GameService.doPlayerTurn($scope.gameTitle, $scope.playerId, $scope.playerType, {}, onDoComputerSecondTurnSuccess, onDoPlayerTurnError)
                }

                function onDoComputerSecondTurnSuccess() {
                    GameService.getPlayerBoard($scope.gameTitle, $scope.playerId, onGetPlayerBoardSuccess, onGetPlayerBoardError);
                }

                function extractGameInfo(response) {
                    $scope.playerList = response["playersInfo"];
                    $scope.Game.round = response["round"];
                    $scope.Game.playerMove = response["moves"];
                    $scope.currentGameStatus = response["currentGameStatus"];
                    $scope.totalRounds = response["totalRounds"];
                    $scope.currentPlayerId = response["currentPlayerId"];
                    $scope.isGameStarted = response["isGameStarted"];
                    $scope.isGameEnded = response["isGameEnded"];
                }

                function onGetGameInfoInitialSuccess(response) {
                    $scope.playerList = response["playersInfo"];
                    if ($scope.playerId == null) {
                        for (var i = 0; i < $scope.playerList.length; ++i) {
                            if ($rootScope.globals.currentUser == $scope.playerList[i].name) {
                                $scope.playerId = i;
                                $scope.playerType = $scope.playerList[i].type;
                            }
                        }
                    }
                    extractGameInfo(response);
                }

                function onGetGameInfoError(response) {
                }

                function onGetPlayerBoardSuccess(response) {
                    for (var i = 0; i < response.length; ++i) {
                        for (var j = 0; j < response[i].length; ++j) {
                            $scope.playerBoard[i][j].color = response[i][j];
                            $scope.playerBoard[i][j].isSelected = false;
                        }
                    }
                }

                function onGetPlayerBoardError(response) {

                }

                function onDoPlayerTurnSuccess() {
                    GameService.getPlayerBoard($scope.gameTitle, $scope.playerId, onGetPlayerBoardSuccess, onGetPlayerBoardError);
                }

                function onDoPlayerTurnError(response) {

                }

                function onDoPlayerUndoTurnSuccess() {
                    GameService.getPlayerBoard($scope.gameTitle, $scope.playerId, onGetPlayerBoardSuccess, onGetPlayerBoardError);
                }

                function onDoPlayerUndoTurnError(response) {

                }

                $scope.onSquareClicked = function (row, column) {
                    var key;
                    key = '(' + row + ',' + column + ')';
                    if (!$scope.moveMap.has(key)) {
                        $scope.playerBoard[row][column].isSelected = true;
                        var value = {};
                        value["row"] = row;
                        value["column"] = column;
                        $scope.moveMap.set(key, value);
                    } else {
                        $scope.playerBoard[row][column].isSelected = false;
                        $scope.moveMap.delete(key);
                    }

                };

                $scope.doUndo = function () {
                    GameService.doPlayerUndoTurn($scope.gameTitle, $scope.playerId, onDoPlayerUndoTurnSuccess, onDoPlayerUndoTurnError)
                };

                $scope.doTurn = function () {
                    var playerTurn = [];
                    var movesItr = $scope.moveMap.values();
                    var currentMove;
                    while ((currentMove = movesItr.next().value) != null) {
                        var moveToAdd = {
                            "row": currentMove.row,
                            "column": currentMove.column,
                            "newBoardSquare": $scope.boardSquare,
                            "previousBoardSquare": $scope.boardSquare
                        };
                        playerTurn.push(moveToAdd)
                    }
                    $scope.moveMap.clear();
                    GameService.doPlayerTurn($scope.gameTitle, $scope.playerId, $scope.playerType, playerTurn, onDoPlayerTurnSuccess, onDoPlayerTurnError)
                };

                $scope.logout = function () {
                    unregisterPlayerFormGame();
                    GameService.Logout($scope.playerName, onLogoutSuccess);
                };

                function unregisterPlayerFormGame() {

                }

                function onLogoutSuccess() {
                    $location.url($location.path('/login'));
                }

                function init() {
                    getInitialPageResources();
                    $scope.pageRefrshInterval = setInterval(getPageResources, 2000);
                }

                init();

            }

        ])
;