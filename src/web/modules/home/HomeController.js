'use strict';
 
angular.module('Home')
 
.controller('HomeController',
    ['$scope',
    function ($scope) {
        $scope.UserList = [
            {name: "Idushi", type: 'Human'},
            {name: "Amitai", type: 'Computer'}
            ];

        $scope.uploadFile = function () {
          //  UploadFileService.UploadFile();
        };

        $scope.uploadGame = function () {
         //   LoadGameService.UploadGame();
        };
      
    }]);