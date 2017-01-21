/**
 * Created by michaelwomack on 1/21/17.
 */

angular.module('app').controller('ProfileController',
    function (Authentication) {

        this.logout = () => {
            Authentication.logout();
        }
    });