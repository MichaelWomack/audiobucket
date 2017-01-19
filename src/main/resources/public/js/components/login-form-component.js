/**
 * Created by michaelwomack on 1/8/17.
 */



angular.module('app').component('loginForm', {
    bindings: {
       toggleRegisterForm: '&'
    },
    templateUrl: 'html/templates/login-form-template.html',
    controller: class {

        constructor(Authentication) {
            this.auth = Authentication;
            this.email;
            this.password;
        }

        login() {
            this.auth.login(this.email, this.password).then((response) => {
                let message = response.data.message;
                alert(message);
            });
        }

        toggleRegister() {
            this.toggleRegisterForm();
        }
    }
});



