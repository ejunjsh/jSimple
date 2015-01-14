'use strict';

/* Controllers */

angular.module('controllers', [])
    .controller('blogListController', function ($scope, $rootScope, blogService, categoryService, tagService, $routeParams, $location) {

        if (!$routeParams.p)
            $routeParams.p = 1;
        if ($routeParams.tagLinkName) {
            $scope.blogs = blogService.get({queryAction: "getBlogByTagLinkName", p: $routeParams.p, linkName: $routeParams.tagLinkName}, function (pagination) {
                $scope.pagination = {path: $location.path(), pageSize: pagination.pageSize, pageIndex: pagination.currentPage, total: pagination.recordCount};
            });
            $scope.tag = tagService.get({tagId: $routeParams.tagLinkName});
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


        $rootScope.$broadcast('currentCategory');
    })
    .controller('headerController', function ($scope, categoryService, $routeParams) {
        $scope.categories = categoryService.query();
        $scope.$on("currentCategory", function () {
            $scope.cate = categoryService.get({categoryId: $routeParams.categoryLinkName});
        });

    });