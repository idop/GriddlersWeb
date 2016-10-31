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

                service.LeaveGame = function (gameTitle, playerId, successCallback) {

                    $http.post('/leaveGame?gameTitle=' + gameTitle + '&playerId=' + playerId)
                        .success(function () {
                            successCallback();
                        });

                };
                function revertConstraint(Constraints) {
                    for (var i = 0; i < Constraints.length; i++) {
                        for (var j = 0; j < Constraints[i].length; j++) {
                            if( Constraints[i][j] != null){
                                Constraints[i][j].isPerfect =false;
                            }
                        }
                    }
                    return Constraints;
                }

                service.setRowPerfectConstraints = function (rowConstraints, gameBoard) {
                    //do work on rowConstraints
                    rowConstraints = revertConstraint(rowConstraints);
                    var streakLength = 0;
                    var bStreakLength = 0;
                    var wantedStreak = 0;
                    var remainder = 0;
                    var remainderBack = 0;
                    var constraintIndex = 0;
                    var rows = gameBoard.length;
                    var columns = gameBoard[0].length;
                    var maxRConstraints = rowConstraints[0].length;
                    var validConstraint = false;
                    var wasPreviousBlack = false;

                    for (var i = 0; i < rows; i++) {
                        for (var j = 0; j < columns; j++) {
                            // find the wanted streak and the amount of minimum remainder squares needed
                            for (var k = 0; k < maxRConstraints && validConstraint == false; k++) {
                                if (rowConstraints[i][k] != null && rowConstraints[i][k].isPerfect == false) {
                                    validConstraint = true;
                                    wantedStreak = rowConstraints[i][k].constraint;
                                    constraintIndex = k;
                                }
                                if (k < maxRConstraints - 1 && validConstraint == true) {   // if it is not the last row constraint
                                    for (var m = k + 1; m < maxRConstraints; m++) {
                                        if (rowConstraints[i][m] != null) {
                                            remainder += rowConstraints[i][m].constraint + 1;
                                        }
                                    }
                                }
                            }
                            if (gameBoard[i][j].color == "Black") {
                                streakLength++;
                                wasPreviousBlack = true;
                            }
                            else if (gameBoard[i][j].color == "Empty") {
                                wasPreviousBlack = false;
                            }
                            else if (gameBoard[i][j].color == "White") {
                                if (streakLength == wantedStreak && (columns - j - remainder >= 0) && wasPreviousBlack == true) {
                                    rowConstraints[i][constraintIndex].isPerfect = true;
                                    streakLength = 0;
                                    remainder = 0;
                                    validConstraint = false;
                                    wasPreviousBlack = false;
                                }
                            }
                        }
                        validConstraint = false;
                        remainder = 0;
                        streakLength = 0;
                        wasPreviousBlack = false;

                        // now again from end to beginning...
                        for (var j = columns - 1; j >= 0; j--) {
                            // find the wanted streak and the amount of minimum remainder squares needed
                            for (var k = maxRConstraints - 1; k >= 0 && validConstraint == false; k--) {
                                if (rowConstraints[i][k] != null && rowConstraints[i][k].isPerfect == false) {
                                    validConstraint = true;
                                    wantedStreak = rowConstraints[i][k].constraint;
                                    constraintIndex = k;
                                }
                                if (k >= 1 && validConstraint == true) {   // if it is not the last row constraint
                                    for (var m = k - 1; m >= 0; m--) {
                                        if (rowConstraints[i][m] != null) {
                                            remainderBack += rowConstraints[i][m].constraint + 1;
                                        }
                                    }
                                }
                            }
                            if (gameBoard[i][j].color == "Black") {
                                bStreakLength++;
                                wasPreviousBlack = true;
                            }
                            else if (gameBoard[i][j].color == "Empty") {
                                wasPreviousBlack = false;
                            }
                            else if (gameBoard[i][j].color == "White") {
                                if (bStreakLength == wantedStreak && (j - remainderBack >= 0) && wasPreviousBlack == true) {
                                    rowConstraints[i][constraintIndex].isPerfect = true;
                                    bStreakLength = 0;
                                    remainderBack = 0;
                                    validConstraint = false;
                                    wasPreviousBlack = false;
                                }
                            }
                        }
                        validConstraint = false;
                        remainderBack = 0;
                        bStreakLength = 0;
                        wasPreviousBlack = false;
                    }
                    return rowConstraints;
                };

                service.setColumnPerfectConstraints = function (columnConstraints, gameBoard) {
                    //console.log("Column constraints: ");
                    // console.log(columnConstraints);

                    columnConstraints = revertConstraint(columnConstraints);
                    var streakLength = 0;
                    var bStreakLength = 0;
                    var wantedStreak = 0;
                    var remainder = 0;
                    var remainderBack = 0;
                    var constraintIndex = 0;
                    var rows = gameBoard.length;
                    var columns = gameBoard[0].length;
                    var maxCConstraints = columnConstraints.length;
                    //console.log("max column constraints = "+ maxCConstraints);
                    var validConstraint = false;
                    var wasPreviousBlack = false;

                    for (var j = 0; j < columns; j++) {
                        for (var i = 0; i < rows; i++) {
                            // find the wanted streak and the amount of minimum remainder squares needed
                            for (var k = 0; k < maxCConstraints && validConstraint == false; k++) {
                                if (columnConstraints[k][j] != null && columnConstraints[k][j].isPerfect == false) {
                                    validConstraint = true;
                                    wantedStreak = columnConstraints[k][j].constraint;
                                    constraintIndex = k;
                                    //  console.log("wanted column streak is: " + wantedStreak);
                                    //  console.log("column constraint index is: " + constraintIndex);
                                }
                                if (k < maxCConstraints - 1 && validConstraint == true) {   // if it is not the last row constraint
                                    for (var m = k + 1; m < maxCConstraints; m++) {
                                        if (columnConstraints[m][j] != null) {
                                            remainder += columnConstraints[m][j].constraint + 1;
                                            //console.log("remainder in column is: " + remainder);
                                        }
                                    }
                                }
                            }
                            if (gameBoard[i][j].color == "Black") {
                                streakLength++;
                                wasPreviousBlack = true;
                            }
                            else if (gameBoard[i][j].color == "Empty") {
                                wasPreviousBlack = false;
                            }
                            else if (gameBoard[i][j].color == "White") {
                                if (streakLength == wantedStreak && (rows - i - remainder >= 0) && wasPreviousBlack == true) {
                                    // console.log("setting perfect to column: " + j + " on row " + constraintIndex);
                                    columnConstraints[constraintIndex][j].isPerfect = true;
                                    streakLength = 0;
                                    remainder = 0;
                                    validConstraint = false;
                                    wasPreviousBlack = false;
                                }
                            }
                        }
                        validConstraint = false;
                        remainder = 0;
                        streakLength = 0;
                        wasPreviousBlack = false;

                        // now again from end to beginning...
                        for (var i = rows - 1; i >= 0; i--) {
                            // find the wanted streak and the amount of minimum remainder squares needed
                            for (var k = maxCConstraints - 1; k >= 0 && validConstraint == false; k--) {
                                if (columnConstraints[k][j] != null && columnConstraints[k][j].isPerfect == false) {
                                    validConstraint = true;
                                    wantedStreak = columnConstraints[k][j].constraint;
                                    constraintIndex = k;
                                }
                                if (k >= 1 && validConstraint == true) {   // if it is not the last row constraint
                                    for (var m = k - 1; m >= 0; m--) {
                                        if (columnConstraints[m][j] != null) {
                                            remainderBack += columnConstraints[m][j].constraint + 1;
                                        }
                                    }
                                }
                            }
                            if (gameBoard[i][j].color == "Black") {
                                bStreakLength++;
                                wasPreviousBlack = true;
                            }
                            else if (gameBoard[i][j].color == "Empty") {
                                wasPreviousBlack = false;
                            }
                            else if (gameBoard[i][j].color == "White") {
                                if (bStreakLength == wantedStreak && (i - remainderBack >= 0) && wasPreviousBlack == true) {
                                    columnConstraints[constraintIndex][j].isPerfect = true;
                                    bStreakLength = 0;
                                    remainderBack = 0;
                                    validConstraint = false;
                                    wasPreviousBlack = false;
                                }
                            }
                        }
                        validConstraint = false;
                        remainderBack = 0;
                        bStreakLength = 0;
                        wasPreviousBlack = false;
                    }
                    return columnConstraints // don't delete this
                };

                return service;
            }]);
