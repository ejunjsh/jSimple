'use strict';

/* Controllers */

angular.module('controllers', [])
  .controller('leftSideController', ['$scope',function ($scope) {
      $scope.tags = [{ name: "java", linkName: "java" }, { name: "c++", linkName: "cplusplus" }];
      $scope.categories = [{ name: "开发", linkName: "kaifa" }, { name: "个人日记", linkName: "diary" }];
  }])
  .controller('rightSideController', ['$scope', function ($scope) {
      $scope.click = function () {
          alert("this is view 1");
      };
  }])
.controller('rightSideController1', ['$scope', function ($scope) {
    $scope.click = function () {
        
    };
    $scope.$on('$viewContentLoaded', function () {
        SyntaxHighlighter.config.clipboardSwf = '/app/js/lib/sh/clipboard.swf';
        SyntaxHighlighter.highlight();
    });
}])
.controller('blogAddController', ['$scope', function ($scope) {
    $scope.blog = { title: "123", content: "2222", tags: "1231 123", linkName: "taa" };
    $scope.$on('$viewContentLoaded', function () {
      

    });
    $scope.click = function () {
        alert($scope.blog.content);
    };
    $scope.loadEditor = function () {
        var kEditor = KindEditor.create('#kindEditor', {
            cssPath: ['/app/css/kind-code.css'],
            items: [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', '|', 'emoticons', 'image', 'link', 'code'],
            afterBlur: function () {
                this.sync();
                $scope.$apply(function () {
                    var editor = document.getElementById('kindEditor');
                    $scope.blog.content = editor.value;
                });
            }
        });
    }
}])
.controller('categoryAddController', ['$scope','categoryService', function ($scope,categoryService) {
   $scope.add=function(){
	   categoryService.create($scope.category,function(){
		   alert("successful");
	   },function(error,status){
		   alert(error.data);
	   });
   };
}]);