'use strict';
 
angular.module('login')
 
.controller('LoginController',
    ['$scope', '$rootScope', '$location', 'LoginService',
    function ($scope, $rootScope, $location, LoginService) {
        // reset login status
 
        $scope.playerType='Human';
        $scope.login = function () {
            $scope.dataLoading = true;
            LoginService.Login($scope.username, $scope.playerType, loginSuccess,loginError);
        };
        function loginSuccess(response){
            $rootScope.globals.currentUser = $scope.username;
            $scope.dataLoading = false;
            $location.path('/home').search('state', '1').search('playerName', $scope.username);
        }

        function loginError(response){
            $scope.error = response.message;
            $scope.dataLoading = false;
        }
    }]);