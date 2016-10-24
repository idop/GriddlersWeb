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

                $scope.Game = {
                    round: '10',
                    playerMove: '1',
                    playerScore: '98',
                    currentGameStatus: 'The world is going to shits..'
                };

                $scope.PlayerList = [
                    {name: "Idushi", type: "Human", score: "37"},
                    {name: "Amitai", type: "Human", score: "98"},
                    {name: "HAL", type: "Computer", score: "69"}
                ];

                function getPageResources() {
                    GameService.getConstraints($scope.gameTitle, onGetConstraintsSuccess, onGetConstraintsError);
                }

                function onGetConstraintsSuccess(response) {
                    $scope.rowConstraints = response["row"];
                    $scope.columnConstraints = response["column"];
                    console.log($scope.rowConstraints);
                    console.log($scope.columnConstraints);
                }

                function onGetConstraintsError(response) {
                }


                function init() {
                    getPageResources();

                    $scope.pageRefrshInterval = setInterval(getPageResources, 2000);
                }

                init();

            }]);