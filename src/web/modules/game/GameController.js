/**
 * Created by amitaihandler on 10/18/16.
 */
'use strict';

angular.module('Game')

    .controller('GameController',
        ['$scope',
            function ($scope) {
                $scope.Game = [];
                $scope.PlayerList = [];
                $scope.rowConstraints = [];
                $scope.columnConstraints = [];

                $scope.Game = {
                    round :'10',
                    playerMove : '1',
                    playerScore: '98',
                    currentGameStatus: 'The world is going to shits..'
                };

                $scope.PlayerList = [
                    { name: "Idushi", type: "Human", score: "37"},
                    { name: "Amitai", type: "Human", score: "98"},
                    { name: "HAL", type: "Computer", score: "69"}
                ]

                $scope.rowConstraints = [

                ]


            }]);