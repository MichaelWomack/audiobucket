/**
 * Created by michaelwomack on 1/8/17.
 */


angular.module('app').component('loginForm', {
    bindings: {
        data: '='
    },
    templateUrl: 'html/templates/login-form-template.html',
    controller: function (Users) {
        this.email;
        this.password;

        this.login = () => {
            Users.userLogin(this.email, this.password).then((response) => {
                alert(Object.keys(response));
                alert(JSON.stringify(response));
                alert(JSON.stringify(response.config.headers));


            });
        };

        this.register = () => {
        }
    }
});

