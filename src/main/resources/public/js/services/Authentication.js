/**
 * Created by michaelwomack on 1/16/17.
 */


angular.module('app').factory('TokenManager', ($window) => {
    let manager = {};

    manager.setToken = (token) => {
        $window.localStorage.setItem('jwt', token);
    };

    manager.getToken = () => {
        return $window.localStorage.getItem('jwt');
    };

    manager.clearToken = () => {
        $window.localStorage.removeItem('jwt');
    };

    return manager;
}).

factory('Authentication', (TokenManager, $http, $state) => {
    let self = {};

    self.getIdentity = () => {
        return $http.get('/api/users/identity');
    };

    self.isLoggedIn = () => {
        return TokenManager.getToken() != undefined && TokenManager.getToken() != 'undefined';
    };

    self.login = (email, password) => {
        return $http.post('/users/login', {email: email, password: password})
            .then((response) => {
                if (response.data.token) {
                    TokenManager.setToken(response.data.token);
                }

                return response;
            });
    };

    self.logout = () => {
        TokenManager.clearToken();
        $state.go('login');
    };
    
    return self;
}).

factory('JwtInterceptor', (TokenManager, $location, $q) => {
    let self = {};

    self.request = (config) => {
        if (TokenManager.getToken() != undefined) {
            config.headers.Authorization = 'Bearer ' + TokenManager.getToken();
        }
        return config;
    };

    self.responseError = (res) => {
        if (res.status === 401) {
            TokenManager.clearToken();
            $location.path('/');
        }
        return $q.reject(res);
    };

    return self;
});