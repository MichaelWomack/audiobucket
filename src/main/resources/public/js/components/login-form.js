/**
 * Created by michaelwomack on 1/8/17.
 */



angular.module('app').component('loginForm', {
    templateUrl: 'html/templates/login-form-template.html',
    controller: function (Authentication, $state, $mdToast) {
        this.email;
        this.password;
        this.errorToast = $mdToast
            .simple()
            .action("Ok")
            .highlightAction(true)
            .position('top center')
            .hideDelay(8000);

        if (Authentication.isLoggedIn()) {
            $state.go("profile");
        }

        this.login = () => {
            //TODO add spinner.
            Authentication.login(this.email, this.password).then((response) => {
                let message = response.data.message;
                if (response.data.success) {
                    $mdToast.hide();
                    $state.go('profile');
                }
                else {
                    this.errorToast.textContent(message);
                    $mdToast.show(this.errorToast);
                }
            });
        };
    }
});



