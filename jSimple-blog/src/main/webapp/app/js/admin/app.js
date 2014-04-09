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
    $routeProvider.when('/tag/:linkName', { templateUrl: '/app/partials/rightSide.html', controller: 'rightSideController' });
    $routeProvider.when('/category/add', { templateUrl: '/app/partials/categoryAdd.html', controller: 'categoryAddController' });
    $routeProvider.when('/category/:linkName', { templateUrl: '/app/partials/rightSide1.html', controller: 'rightSideController1' });
    $routeProvider.when('/blog/add', { templateUrl: '/app/partials/blogAdd.html', controller: 'blogAddController' });
    $routeProvider.otherwise({ redirectTo: '/view1' });

    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);