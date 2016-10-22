'use strict';

angular.module('Home')

    .controller('HomeController',
        ['$scope', '$rootScope', 'HomeService',
            function ($scope, $rootScope, HomeService) {
                $scope.UserList = [];

                $scope.GameList = [];
                $scope.pageRefrshInterval = 0;

                $scope.uploadFile = function () {
                    var file = $scope.myFile;

                    console.log('file is ' );
                    console.dir(file);

                    HomeService.uploadGame(file);
                };

                $scope.chooseGame = function () {
                    //   HomeService.UploadGame();
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