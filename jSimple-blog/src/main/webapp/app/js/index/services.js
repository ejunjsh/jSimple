'use strict';

angular.module('services', ["ngResource"])
    .factory('categoryService', function ($resource) {
        return $resource("/api/category/:categoryId", {}, {
            query: { method: 'GET', isArray: true },
            get: { method: 'GET'},
            create: { method: 'POST' },
            update: {method: 'PUT'}
        });
    })
    .factory('blogService', function ($resource) {
        return $resource("/api/blog/:queryAction", {}, {
            get: { method: 'GET', isArray: false },
            create: { method: 'POST' },
            update: {method: 'PUT'}
        });
    })
    .factory('tagService', function ($resource, $routeParams) {
        return $resource("/api/tag/:tagId", {}, {
            query: { method: 'GET', isArray: true},
            get: { method: 'GET'},
            update: {method: 'PUT'}
        });
    });