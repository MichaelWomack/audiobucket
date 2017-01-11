/**
 * Created by michaelwomack on 1/8/17.
 */


angular.module('app').component('loginForm', {
    bindings: {
        data: '='
    },
    templateUrl: 'html/templates/login-form-template.html',
    controller: loginFormController
});


function loginFormController() {
    this.email;
    this.password;

    this.login = () => {
    };

    this.register = () => {
    }
}
