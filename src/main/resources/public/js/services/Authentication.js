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
        $state.go('login');
    };
    
    return self;
}).

factory('JwtInterceptor', (TokenManager, $location, $q) => {
    let self = {};

    self.request = (config) => {
        if (TokenManager.getToken() != null) {
            config.headers.Authorization = 'Bearer ' + TokenManager.getToken();
        }
        return config;
    };

    self.responseError = (req) => {
        if (req.config.status === 403) {
            $location.path('/');
        }
        if (req.config.status == 404) {
            alert("Page wasnt found fool");
        }
        return $q.reject(req);
    };

    return self;
});