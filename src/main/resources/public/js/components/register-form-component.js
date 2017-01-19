/**
 * Created by michaelwomack on 1/12/17.
 */

angular.module('app').component('registerForm', {
    templateUrl: 'html/templates/register-form-template.html',
    controller: class {

        constructor(Users) {
            this.UsersService = Users;
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
                alert("Why this aint workin");
                this.UsersService.registerUser(this.email, this.password).then((response) => {
                    let message = response.data.message;
                    alert(JSON.stringify(message));
                });
            }
        }
    }
});