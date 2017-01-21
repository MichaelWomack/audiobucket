/**
 * Created by mxw4182 on 12/16/16.
 */

angular.module('app', ['ui.router', 'ngMaterial'])
    .config(($stateProvider, $urlRouterProvider, $locationProvider,
             $mdThemingProvider, $httpProvider) => {

        $httpProvider.interceptors.push('JwtInterceptor');
        $urlRouterProvider.otherwise('/login');

        $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'html/views/login.html',
            controller: 'LoginController',
            controllerAs: 'LoginCtrl'
        }).state('register', {
            url: '/register',
            templateUrl: 'html/views/register.html'
        }).state('profile', {
            url: '/profile',
            templateUrl: 'html/views/profile.html',
            controller:'ProfileController',
            controllerAs: 'ProfileCtrl'
        });

        $locationProvider.html5Mode(true);

        $mdThemingProvider.theme("default").dark();
    });
