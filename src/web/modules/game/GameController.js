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

                function getPageResources() {
                    GameService.getGameInfo($scope.gameTitle, onGetGameInfoSuccess, onGetGameInfoError);
                    GameService.getConstraints($scope.gameTitle, onGetConstraintsSuccess, onGetConstraintsError);

                }

                function onGetConstraintsSuccess(response) {
                    $scope.rowConstraints = response["row"];
                    $scope.columnConstraints = response["column"];
                }

                function onGetConstraintsError(response) {
                }

                function onGetGameInfoSuccess(response) {
                    $scope.playerList = response["playersInfo"];
                    for (var i = 0; i < $scope.playerList.length; ++i) {
                        if ($rootScope.globals.currentUser == $scope.playerList[i].name) {
                            $scope.playerId = i;
                        }
                        $scope.Game.round = response["round"];
                        $scope.Game.playerMove = response["moves"];
                        $scope.currentGameStatus = response["currentGameStatus"];
                        $scope.totalRounds = response["totalRounds"];
                        $scope.currentPlayerId = response["currentPlayerId"];
                        $scope.isGameStarted = response["isGameStarted"];
                        $scope.isGameEnded = response["isGameEnded"];
                    }
                }

                function onGetGameInfoError(response) {
                }


                function init() {

                    getPageResources();

                    // $scope.pageRefrshInterval = setInterval(getPageResources, 2000);
                }

                init();

            }]);