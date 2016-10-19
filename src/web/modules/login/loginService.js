'use strict';

angular.module('login')

    .factory('LoginService',
        ['$http', '$cookieStore', '$rootScope',
            function ($http, $cookieStore, $rootScope) {
                var service = [];

                service.Login = function (username, playerType, successCallback, errorCallback) {

                    $http.post('/login?username=' + username + '&playerType=' + playerType)
                        .success(function (response) {
                            successCallback(response);
                        }).error(function (response) {
                        console.log(response);
                        console.log(response.message)
                        errorCallback(response);

                    });

                };
                return service;
            }])
