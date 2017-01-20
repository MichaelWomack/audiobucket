/**
 * Created by michaelwomack on 1/8/17.
 */



angular.module('app').component('loginForm', {
    bindings: {
       toggleRegisterForm: '&'
    },
    templateUrl: 'html/templates/login-form-template.html',
    controller: function (Authentication, $state) {
        this.email;
        this.password;

        this.login = () => {
            Authentication.login(this.email, this.password).then((response) => {
                let message = response.data.message;
                if (response.data.success) {
                    alert(response.data.token);
                    $state.go('profile');
                }
            });
        };
    }
});



