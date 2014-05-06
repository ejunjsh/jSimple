'use strict';


// Declare app level module which depends on filters, and services
angular.module('myapp', [
  'ngRoute',
  'filters',
  'services',
  'directives',
  'controllers'
],function($httpProvider){
	$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
	
	 var param = function(obj) {
		    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
		      
		    for(name in obj) {
		      value = obj[name];
		        
		      if(value instanceof Array) {
		        for(i=0; i<value.length; ++i) {
		          subValue = value[i];
		          fullSubName = name + '[' + i + ']';
		          innerObj = {};
		          innerObj[fullSubName] = subValue;
		          query += param(innerObj) + '&';
		        }
		      }
		      else if(value instanceof Object) {
		        for(subName in value) {
		          subValue = value[subName];
		          fullSubName = name + '[' + subName + ']';
		          innerObj = {};
		          innerObj[fullSubName] = subValue;
		          query += param(innerObj) + '&';
		        }
		      }
		      else if(value !== undefined && value !== null)
		        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
		    }
		      
		    return query.length ? query.substr(0, query.length - 1) : query;
		  };

		  // Override $http service's default transformRequest
		  $httpProvider.defaults.transformRequest = [function(data) {
		    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
		  }];
}).
config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider.when('/admin/tag/:linkName/edit', { templateUrl: '/app/partials/tagUpdate.html', controller: 'tagController' });
    $routeProvider.when('/admin/category/add', { templateUrl: '/app/partials/categoryAdd.html', controller: 'categoryController' });
    $routeProvider.when('/admin/category/:linkName/edit', { templateUrl: '/app/partials/categoryEdit.html', controller: 'categoryController' });
    $routeProvider.when('/admin/blog/add', { templateUrl: '/app/partials/blogAdd.html', controller: 'blogController' });
    $routeProvider.when('/admin/blog/:linkName/edit', { templateUrl: '/app/partials/blogEdit.html', controller: 'blogController' });
    $routeProvider.when('/admin/blog/list', { templateUrl: '/app/partials/blogList.html', controller: 'blogListController' });
    $routeProvider.when('/admin/blog/:linkName', { templateUrl: '/app/partials/blogDetail.html', controller: 'blogController' });
    $routeProvider.otherwise({ redirectTo: '/admin/blog/add' });
    $routeProvider.when('/admin/tag/:tagLinkName', { templateUrl: '/app/partials/blogList.html', controller: 'blogListController' });
    $routeProvider.when('/admin/category/:categoryLinkName', { templateUrl: '/app/partials/blogList.html', controller: 'blogListController' });
    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);