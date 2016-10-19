'use strict';
 
angular.module('Home')
 
.controller('HomeController',
    ['$scope','UploadFileService',
    function ($scope, UploadFileService) {
        $scope.uploadFile = function () {
            UploadFileService.UploadFile();
        };

        $scope.uploadGame = function () {
            LoadGameService.UploadGame();
        };
      
    }]);