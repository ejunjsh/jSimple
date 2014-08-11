'use strict';

/* Controllers */

angular.module('controllers', [])
    .controller('leftSideController', function ($scope, tagService, categoryService) {
        $scope.reload = function () {
            $scope.tags = tagService.query();
            $scope.categories = categoryService.query();
        };
        $scope.reload();
        $scope.$on("reloadLeftSide", function () {
            $scope.reload();
        });
    })
    .controller('tagController', function ($scope, $routeParams, tagService, $rootScope, $location) {
        $scope.tag = tagService.get({tagId: $routeParams.linkName});
        $scope.update = function () {
            tagService.update($scope.tag, function () {
                alert("successful");
                $rootScope.$broadcast('reloadLeftSide');
                $location.path("/admin/tag/" + $scope.tag.linkName + "/edit");
            }, function (error, status) {
                alert(error.data);
            });
        };
    })
    .controller('blogListController', function ($scope, blogService, $routeParams, $location) {

        if (!$routeParams.p)
            $routeParams.p = 1;
        if ($routeParams.tagLinkName) {
            $scope.blogs = blogService.get({queryAction: "getBlogByTagLinkName", p: $routeParams.p, linkName: $routeParams.tagLinkName}, function (pagination) {
                $scope.pagination = {path: $location.path(), pageSize: pagination.pageSize, pageIndex: pagination.currentPage, total: pagination.recordCount};
            });
        }
        else if ($routeParams.categoryLinkName) {
            $scope.blogs = blogService.get({queryAction: "getBlogByCategoryLinkName", p: $routeParams.p, linkName: $routeParams.categoryLinkName}, function (pagination) {
                $scope.pagination = {path: $location.path(), pageSize: pagination.pageSize, pageIndex: pagination.currentPage, total: pagination.recordCount};
            });
        }
        else {
            $scope.blogs = blogService.get({queryAction: "getAllBlog", p: $routeParams.p}, function (pagination) {
                $scope.pagination = {path: $location.path(), pageSize: pagination.pageSize, pageIndex: pagination.currentPage, total: pagination.recordCount};
            });
        }
    })
    .controller('blogController', function ($scope, categoryService, blogService, $routeParams, $location, $rootScope) {


        if ($routeParams.linkName) {
            $scope.blog = blogService.get({queryAction: $routeParams.linkName});
        }
        $scope.update = function () {
            blogService.update($scope.blog, function () {
                alert("successful");
                $rootScope.$broadcast('reloadLeftSide');
                $location.path("/admin/blog/" + $scope.blog.linkName + "/edit");
            }, function (error, status) {
                alert(error.data);
            });
        };

        $scope.categories = categoryService.query();
        $scope.$on('$viewContentLoaded', function () {

        });
        $scope.add = function () {
            blogService.create($scope.blog, function () {
                alert("successful");
                $rootScope.$broadcast('reloadLeftSide');
                $location.path("/admin/blog/" + $scope.blog.linkName + "/edit");
            }, function (error, status) {
                alert(error.data);
            });
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
                },
                uploadJson: '/api/upload'
            });
        };

    })
    .controller('categoryController', function ($scope, categoryService, $routeParams, $rootScope, $location) {
        $scope.add = function () {
            categoryService.create($scope.category, function () {
                alert("successful");
                $rootScope.$broadcast('reloadLeftSide');
                $location.path("/admin/category/" + $scope.category.linkName + "/edit");
            }, function (error, status) {
                alert(error.data);
            });
        };
        if ($routeParams.linkName) {
            $scope.category = categoryService.get({categoryId: $routeParams.linkName});
        }
        $scope.update = function () {
            categoryService.update($scope.category, function () {
                alert("successful");
                $rootScope.$broadcast('reloadLeftSide');
                $location.path("/admin/category/" + $scope.category.linkName + "/edit");
            }, function (error, status) {
                alert(error.data);
            });
        };
    });