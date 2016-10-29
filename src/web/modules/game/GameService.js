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

                    $http.get('/getPlayerBoard?gameTitle=' + gameTitle + '&playerId=' + playerId)
                        .success(function (response) {
                            successCallback(response);
                        }).error(function (response) {
                        errorCallback(response);
                    });

                };
                service.doPlayerTurn = function (gameTitle, playerId, playerType, playerTurn, successCallback, errorCallback) {
                    $http.post('/doPlayerTurn?gameTitle=' + gameTitle + '&playerId=' + playerId + '&playerType=' + playerType, playerTurn, {headers: {'Content-Type': 'application/json'}})
                        .success(function () {
                            successCallback();
                        }).error(function (response) {
                        errorCallback(response);
                    });
                };

                service.doPlayerUndoTurn = function (gameTitle, playerId, successCallback, errorCallback) {
                    $http.post('/doPlayerUndoTurn?gameTitle=' + gameTitle + '&playerId=' + playerId)
                        .success(function () {
                            successCallback();
                        }).error(function (response) {
                        errorCallback(response);
                    });

                };

                service.Logout = function (username, successCallback) {

                    $http.post('/logout?username=' + username)
                        .success(function () {
                            successCallback();
                        });

                };

                service.LeaveGame = function (gameTitle, playerId,  successCallback) {

                    $http.post('/leaveGame?gameTitle=' + gameTitle + '&playerId=' + playerId)
                        .success(function () {
                            successCallback();
                        });

                };

                service.setRowPerfectConstraints =  function(rowConstraints,gamaBoard){
                    //do work on rowConstraints

                    return rowConstraints;
                };

                service.setColumnPerfectConstraints =  function(columnConstraints,gamaBoard){
                    //do work on columnConstraints

                    return columnConstraints;
                };

                return service;
            }]);
