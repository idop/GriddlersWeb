/**
 * Created by idope on 10/22/2016.
 */
'use strict';

angular.module('Home')

    .factory('HomeService',
        ['$http', '$cookieStore', '$rootScope',
            function ($http, $cookieStore, $rootScope) {
                var service = [];

                service.getUserList = function (successCallback) {

                    $http.get('/players')
                        .success(function (response) {
                            successCallback(response);
                        })

                };

                service.getGameList = function (successCallback) {

                    $http.get('/games')
                        .success(function (response) {
                            successCallback(response);
                        })
                };

                service.uploadGame = function (file,successCallback,errorCallback) {
                    var fd = new FormData();
                    fd.append('file', file);

                    $http.post('/uploadGame?username='+$rootScope.globals.currentUser, fd, {
                        transformRequest: angular.identity,
                        headers: {'Content-Type': undefined}
                    })

                        .success(function () {
                            successCallback();
                        })

                        .error(function (response) {
                            errorCallback(response);
                        })
                };

                service.registerToGame = function (game,successCallback,errorCallback) {

                    $http.post('/registerToGame?username='+$rootScope.globals.currentUser+'&gameTitle=' +game.title)
                        .success(function (response) {
                            successCallback(response);
                        })
                        .error(function (response) {
                            errorCallback(response);
                        })
                };

                service.Logout =function (username, successCallback) {

                    $http.post('/logout?username=' + username )
                        .success(function () {
                            successCallback();
                        });

                };
                return service;
            }])
