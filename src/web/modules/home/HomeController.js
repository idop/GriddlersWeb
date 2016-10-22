'use strict';

angular.module('Home')

    .controller('HomeController',
        ['$scope', '$rootScope', 'HomeService',
            function ($scope, $rootScope, HomeService) {
                $scope.UserList = [];

                $scope.GameList = [];
                $scope.pageRefrshInterval = 0;

                $scope.uploadFile = function () {
                    //  UploadFileService.UploadFile();
                };

                $scope.uploadGame = function () {
                    //   LoadGameService.UploadGame();
                };

                function getPageResources() {
                    HomeService.getUserList(getUserListCallBack);
                    HomeService.getGameList(getGameListCallBack);
                }


                function getUserListCallBack(response) {
                    $scope.UserList = response;
                }

                function getGameListCallBack(response) {
                    $scope.GameList = response;
                }

                function init() {
                    getPageResources();
                    $scope.pageRefrshInterval = setInterval(getPageResources, 2000);
                }

                init();

            }]);