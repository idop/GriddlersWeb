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
                $scope.pageRefrshInterval = null;
                $scope.boardSquare = 'Black';
                $scope.playerBoard = [[]];
                $scope.moveMap = null;
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
                            }
                        }
                    }
                    extractGameInfo(response);
                }

                function onGetGameInfoError(response) {
                }

                function onGetPlayerBoardSuccess(response) {
                    console.log(response);

                    for (var i = 0; i < rowConstraints.length; ++i) {
                        for (var j = 0; j < columnConstraints[0].length; ++j) {
                            $scope.playerBoard[i][j].color = response[i][j];
                            $scope.playerBoard[i][j].isSelected = false;
                        }
                    }
                }

                function onGetPlayerBoardError(response) {

                }

                function ondoPlayerTurnSuccess(response) {
                    GameService.getPlayerBoard($scope.gameTitle, $scope.playerId, onGetPlayerBoardSuccess, onGetPlayerBoardError);
                }

                function onDoPlayerTurnError(response) {

                }

                function ondoPlayerUndoTurnSuccess(response) {
                    GameService.getPlayerBoard($scope.gameTitle, $scope.playerId, onGetPlayerBoardSuccess, onGetPlayerBoardError);
                }

                function onDoPlayerUndoTurnError(response) {

                }

                $scope.onSquareClicked = function (row, column) {
                    if ($scope.moveMap == null) {
                        $scope.moveMap = new Map();
                    }

                    var key;
                    key = '(' + row + ',' + column + ')';
                    if (!$scope.moveMap.has(key)) {
                        $scope.playerBoard[row][column].isSelected = true;
                        var value = {};
                        value["row"] = row;
                        value["column"] = column;
                        value["BoardSquare"] = $scope.boardSquare
                        $scope.moveMap.set(key, value);
                    } else {
                        $scope.playerBoard[row][column].isSelected = false;
                        $scope.moveMap.delete(key);
                    }


                    console.log($scope.moveMap);

                };

                function init() {
                    getInitialPageResources();
                    $scope.pageRefrshInterval = setInterval(getPageResources, 2000);
                }

                init();

            }]);