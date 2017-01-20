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

factory('Authentication', (TokenManager, $http) => {
    let self = {};

    self.isLoggedIn = () => {
        return TokenManager.getToken() != undefined;
    };

    self.login = (email, password) => {
        return $http.post('/users/login', {email: email, password: password})
            .then((response) => {
                TokenManager.setToken(response.data.token);
                return response;
            });
    };

    self.logout = () => {
        TokenManager.clearToken();
    };
    
    return self;
}).

factory('JwtInterceptor', (TokenManager, $location) => {
    let self = {};

    self.request = (config) => {
        if (TokenManager.getToken()) {
            config.headers.Authorization = 'Bearer ' + TokenManager.getToken();
        }
        return config;
    };

    self.requestError = (req) => {
        if (req.config.status === 403) {
            $location.path('/');
        }
        return req;
    };

    return self;
});