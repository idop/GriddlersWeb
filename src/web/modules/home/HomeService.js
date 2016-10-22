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
                return service;
            }])
