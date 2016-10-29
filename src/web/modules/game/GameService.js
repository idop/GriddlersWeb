/**
 * Created by idope on 10/22/2016.
 */
'use strict';

angular.module('Game')

    .factory('GameService',
        ['$http',
            function ($http) {
                var service = [];

                service.getConstraints = function (gameTitle, successCallback, errorCallback) {

                    $http.get('/getConstraints?gameTitle=' + gameTitle)
                        .success(function (response) {
                            successCallback(response);
                        }).error(function (response) {
                        errorCallback(response);
                    });

                };

                service.getGameInfo = function (gameTitle, successCallback, errorCallback) {

                    $http.get('/getActiveGameInfo?gameTitle=' + gameTitle)
                        .success(function (response) {
                            successCallback(response);
                        }).error(function (response) {
                        errorCallback(response);
                    });

                };


                service.getPlayerBoard = function (gameTitle, playerId, successCallback, errorCallback) {

                    $http.get('/getPlayerBoard?gameTitle=' + gameTitle+ '&playerId=' + playerId)
                        .success(function (response) {
                            successCallback(response);
                        }).error(function (response) {
                        errorCallback(response);
                    });

                };
                service.doPlayerTurn = function (gameTitle, playerId, playerTurn, successCallback, errorCallback) {
                    $http.get('/doPlayerTurn?gameTitle=' + gameTitle + '&playerId=' + playerId)
                        .success(function (response) {
                            successCallback(response);
                        }).error(function (response) {
                        errorCallback(response);
                    });

                };

                service.doPlayerUndoTurn = function (gameTitle, playerId, successCallback, errorCallback) {
                    $http.get('/doPlayerUndoTurn?gameTitle=' + gameTitle + '&playerId=' + playerId)
                        .success(function (response) {
                            successCallback(response);
                        }).error(function (response) {
                        errorCallback(response);
                    });

                };

                return service;
            }]);
