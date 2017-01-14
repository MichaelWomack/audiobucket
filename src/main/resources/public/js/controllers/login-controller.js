/**
 * Created by michaelwomack on 1/10/17.
 */

angular.module('app').controller('LoginController', function() {

    this.showRegisterForm = false;

    this.toggleRegisterForm = () => {
        this.showRegisterForm = !this.showRegisterForm;
    };
});