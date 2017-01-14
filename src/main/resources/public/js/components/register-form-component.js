/**
 * Created by michaelwomack on 1/12/17.
 */

angular.module('app').component('registerForm', {
    templateUrl: 'html/templates/register-form-template.html',
    controller: class {

        constructor(Users) {
            this.Users = Users;
            this.email;
            this.password;
            this.confirmPassword;
        }

        register() {
            if (this.password != this.confirmPassword) {
                /** Log error message **/
                alert("Passwords don't match!");
            }
            else {
                this.Users.registerUser(this.email, this.password).then((response) => {
                    let result = response.config.data;
                });
            }
        }
    }
});