/**
 * Created by michaelwomack on 1/21/17.
 */

angular.module('app').controller('ProfileController',
    function (Authentication) {
        Authentication.getIdentity().then((response) => {
            alert(JSON.stringify(response));
        });
        this.logout = () => {
            Authentication.logout();
        }
    });