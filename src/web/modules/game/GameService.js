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

                service.setRowPerfectConstraints =  function(rowConstraints,gameBoard){
                    //do work on rowConstraints
                    console.log(gameBoard);
                    console.log("number of rows is: " + gameBoard.length);
                    console.log(rowConstraints);
                    var streakLength    = 0;
                    var bStreakLength   = 0;
                    var wantedStreak    = 0;
                    var remainder       = 0;
                    var remainderBack   = 0;
                    var constraintIndex = 0;
                    var rows            = gameBoard.length;
                    var columns         = gameBoard[0].length;
                    var maxRConstraints = rowConstraints[0].length;

                    for(var i = 0; i < rows; i++){
                        for(var j = 0; j < columns; j++){
                            // find the wanted streak and the amount of minimum remainder squares needed
                            for(var k =0; k < maxRConstraints; k++){
                                if(rowConstraints[i][k] != null && rowConstraints[i][k].isPerfect == false){
                                    wantedStreak = rowConstraints[i][k].constraint;
                                    constraintIndex = k;
                                }
                                if(k < maxRConstraints - 1){   // if it is not the last row constraint
                                    for(var m = k + 1; m < maxRConstraints; m++){
                                        if(rowConstraints[i][m] != null){
                                            remainder += rowConstraints[i][m].constraint + 1;
                                        }
                                    }
                                }
                            }
                            if(gameBoard[i][j].color == "Black"){
                                streakLength++;
                            }
                            else if (gameBoard[i][j].color == "White"){
                                if (streakLength == wantedStreak && (columns - j - remainder >= 0)){
                                    rowConstraints[i][constraintIndex].isPerfect = true;
                                    streakLength = 0;
                                    remainder = 0;
                                }
                            }
                        }
                        // now again from end to beginning...
                        for(var j = columns - 1; j >= 0; j--){
                            // find the wanted streak and the amount of minimum remainder squares needed
                            for(var k = maxRConstraints - 1; k >= 0; k--){
                                if(rowConstraints[i][k] != null && rowConstraints[i][k].isPerfect == false){
                                    wantedStreak = rowConstraints[i][k].constraint;
                                    constraintIndex = k;
                                }
                                if(k >= 1){   // if it is not the last row constraint
                                    for(var m = k - 1; m >= 0; m--){
                                        if(rowConstraints[i][m] != null){
                                            remainderBack += rowConstraints[i][m].constraint + 1;
                                        }
                                    }
                                }
                            }
                            if(gameBoard[i][j].color == "Black"){
                                bStreakLength++;
                            }
                            else if (gameBoard[i][j].color == "White"){
                                if (streakLength == wantedStreak && (j - remainder >= 0)){
                                    rowConstraints[i][constraintIndex].isPerfect = true;
                                    bStreakLength = 0;
                                    remainderBack = 0;
                                }
                            }
                        }
                    }
                    return rowConstraints;
                };

                service.setColumnPerfectConstraints =  function(columnConstraints,gameBoard){
                    //do work on columnConstraints
                    /*
                    console.log(gameBoard);
                    console.log("number of columns is: " + gameBoard[0].length);
                    console.log(columnConstraints);
                    */
                };

                return service;
            }]);
