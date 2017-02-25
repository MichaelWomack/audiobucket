/**
 * Created by michaelwomack on 1/10/17.
 */

angular.module('app').factory('Users', ($http) => {
    
    let service = {};
 
    service.registerUser = (email, password) => {
        return $http.post('/users/register', {email: email, password: password});
    };
    
    service.updateUser = (user) => {
        return $http.put(`/api/users`, user);  
    };
    
    return service;
});