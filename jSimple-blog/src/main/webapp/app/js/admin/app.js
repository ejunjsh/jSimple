'use strict';


// Declare app level module which depends on filters, and services
angular.module('myapp', [
  'ngRoute',
  'filters',
  'services',
  'directives',
  'controllers'
]).
config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider.when('/tag/:linkName', { templateUrl: '/app/partials/rightSide.html', controller: 'rightSideController' });
    $routeProvider.when('/category/add', { templateUrl: '/app/partials/categoryAdd.html', controller: 'categoryAddController' });
    $routeProvider.when('/category/:linkName', { templateUrl: '/app/partials/rightSide1.html', controller: 'rightSideController1' });
    $routeProvider.when('/blog/add', { templateUrl: '/app/partials/blogAdd.html', controller: 'blogAddController' });
    $routeProvider.otherwise({ redirectTo: '/view1' });

    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
}]);