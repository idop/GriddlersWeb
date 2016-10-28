/**
 * Created by amitaihandler on 10/18/16.
 */
'use strict';

angular.module('Game')

    .controller('GameController',
        ['$scope', '$rootScope', '$location', 'GameService',
            function ($scope, $rootScope, $location, GameService) {
                $scope.gameTitle = ($location.search()).title;
                $scope.Game = [];
                $scope.PlayerList = [];
                $scope.rowConstraints = [];
                $scope.columnConstraints = [];
                $scope.playerId = null;
                $scope.totalRounds = null;
                $scope.Game = {};
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
                    console.log(response["playersInfo"]);
                    $scope.playerList = response["playersInfo"];
                    for (var i = 0; i < playerList.length; ++i) {
                        if(playerList)
                        if ($rootScope.globals.currentUser == playerList[i].name) {
                            $scope.playerId = i;
                        }
                        $scope.Game.round = response["round"];
                        $scope.Game.playerMove = response["moves"];
                        $scope.Game.currentGameStatus = response["currentGameStatus"];
                        $scope.totalRounds = response["totalRounds"];
                        $scope.currentPlayerId = response["currentPlayerId"];
                        $scope.isGameStarted = response["isGameStarted"];
                        $scope.isGameEnded = response["isGameEnded"];
                    }

                    console.log( $scope.playerId);
                }

                function onGetGameInfoError(response) {
                }


                function init() {

                    getPageResources();

                    // $scope.pageRefrshInterval = setInterval(getPageResources, 2000);
                }

                init();

            }]);