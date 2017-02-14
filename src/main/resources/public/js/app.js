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
            template: '<register></register>'
        }).state('profile', {
            url: '/profile',
            templateUrl: 'html/views/profile.html',
            controller: 'ProfileController',
            controllerAs: 'ProfileCtrl',
            resolve: {
                user: function (Authentication) {
                    return Authentication.getIdentity();
                }
            }
        });

        $locationProvider.html5Mode(true);

        //$mdThemingProvider.theme("default").dark();
    });
