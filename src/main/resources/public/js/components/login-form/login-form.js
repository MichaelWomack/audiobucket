/**
 * Created by michaelwomack on 1/8/17.
 */



angular.module('app').component('loginForm', {
    templateUrl: 'js/components/login-form/login-form.html',
    controller: function (Authentication, $state, $mdToast) {
        this.email;
        this.password;
        this.spinnerActive = false;
        this.errorToast = $mdToast
            .simple()
            .action("Ok")
            .highlightAction(true)
            .position('top right')
            .hideDelay(8000);

        if (Authentication.isLoggedIn()) {
            $state.go("profile");
        }

        this.login = () => {
            this.spinnerActive = true;
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
                this.spinnerActive = false;
            });
        };
    }
});



