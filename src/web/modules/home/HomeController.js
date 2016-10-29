'use strict';

angular.module('Home')

    .controller('HomeController',
        ['$scope', '$rootScope', '$location', 'HomeService',
            function ($scope, $rootScope, $location, HomeService) {
                $scope.UserList = [];
                $scope.GameList = [];
                $scope.pageRefrshInterval = 0;
                $scope.selectedGame = null;
                $scope.isGameSelected = false;
                $scope.selectedGameId = null;
                $scope.playerName = "";


                $scope.selectGame = function (game, id) {
                    if ($scope.selectedGame == game) {
                        $scope.isGameSelected = false;
                        $scope.selectedGameId = null;
                    } else {
                        $scope.selectedGame = game;
                        $scope.isGameSelected = true;
                        $scope.selectedGameId = id;
                    }
                };

                $scope.filter('displayGame', function() {
                    return function(input) {
                        var out = [];
                        for (var i = 0; i < input.length; i++) {
                            if(input[i].display == true){
                                out.push(input[i]);
                            }
                        }
                        return out;
                    }
                });


                $scope.uploadFile = function () {
                    var file = $scope.myFile;

                    HomeService.uploadGame(file, onFileUploadSuccess, onFileUploadError);
                };

                $scope.logout = function () {

                    HomeService.Logout($scope.playerName, onLogoutSuccess);
                };

                function onLogoutSuccess(){
                    $location.url($location.path('/login'));
                }

                $scope.chooseGame = function () {
                    if ($scope.selectedGame != null) {
                        HomeService.registerToGame($scope.selectedGame, onChooseGameSuccess, onChooseGameError);
                    }
                };

                function onChooseGameSuccess(response) {
                    $location.path('/game').search('state', '2').search('title', $scope.selectedGame.title);
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
                    $scope.GameList = [
                        {title:"simple game", uploadedBy: "amitai", totalMoves: "15", rows: "10", columns: "15", numberOfRegisteredPlayers: "1", numberOfPlayers: "1", display: "true"},
                        {title:"simple game2", uploadedBy: "Ido", totalMoves: "15", rows: "10", columns: "15", numberOfRegisteredPlayers: "1", numberOfPlayers: "1", display: "false"}
                    ];
                }


                function init() {
                    $scope.playerName = ($location.search()).playerName;
                    getPageResources();
                    $scope.pageRefrshInterval = setInterval(getPageResources, 2000);
                }


                init();

            }]);