/**
 * Created by mxw4182 on 12/16/16.
 */

angular.module('app', ['ui.router', 'ngMaterial'])
    .config(($stateProvider, $urlRouterProvider, $locationProvider) => {

        $urlRouterProvider.otherwise('/login');

        $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'html/views/login.html',
            controller: 'LoginController',
            controllerAs: 'LoginCtrl'
        });

        $locationProvider.html5Mode(true);
    });
