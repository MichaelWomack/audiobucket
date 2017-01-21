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

        if (Authentication.isLoggedIn()) {
            $state.go("profile");
        }
        
        this.login = () => {
            //TODO add spinner.
            Authentication.login(this.email, this.password).then((response) => {
                let message = response.data.message;
                if (response.data.success) {
                    $state.go('profile');
                }
                else {
                    alert(message);
                    alert(JSON.stringify(response));
                }
            });
        };
    }
});



