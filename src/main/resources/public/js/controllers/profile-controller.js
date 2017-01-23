/**
 * Created by michaelwomack on 1/21/17.
 */

angular.module('app').controller('ProfileController', function (Authentication) {

    Authentication.getIdentity().then((response) => {
        this.user = response.data.user;
        console.log(this.user);
    });

    this.logout = () => {
        Authentication.logout();
    }
});