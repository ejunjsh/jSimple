'use strict';

angular.module('services', ["ngResource"])  
.factory('categoryService', function ($resource){  
    return $resource("/api/category", {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    });  
});  