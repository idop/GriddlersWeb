'use strict';

angular.module('Home')

    .controller('HomeController',
        ['$scope', '$rootScope', '$location', 'HomeService',
            function ($scope, $rootScope, $location, HomeService) {
                $scope.UserList = [];

                $scope.GameList = [];
                $scope.pageRefrshInterval = 0;
                $scope.selectedGame = null;
                $scope.isGameNotSelected = true;

                $scope.uploadFile = function () {
                    var file = $scope.myFile;

                    HomeService.uploadGame(file, onFileUploadSuccess, onFileUploadError);
                };

                $scope.chooseGame = function () {
                    HomeService.registerToGame($scope.selectedGame, onChooseGameSuccess, onChooseGameError);
                };

                function onChooseGameSuccess(response) {
                    $location.path('/game').search('title', $scope.selectedGame.title);
                }

                function onChooseGameError(response) {
                    $scope.error = response.message;
                }

                function getPageResources() {
                    HomeService.getUserList(getUserListCallBack);
                    HomeService.getGameList(getGameListCallBack);
                }

                function onFileUploadSuccess() {
                    $scope.error = null;
                    init();
                }

                function onFileUploadError(response) {
                    $scope.error = response.message;
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

                $scope.selectedGameId = null;
                $scope.selectGame = function (game, id) {
                    $scope.selectedGame = game;
                    if (game == null || game == undefined) {
                        $scope.isGameNotSelected = true
                        $scope.selectedGameId = null;
                    } else {
                        $scope.isGameNotSelected = false;
                        $scope.selectedGameId = id;
                    }
                };

                init();

            }]);