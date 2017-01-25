/**
 * Created by michaelwomack on 1/23/17.
 */

angular.module('app').component('userToolbar', {

    templateUrl: 'html/templates/user-toolbar.html',
    controller: function (Authentication, $mdDialog) {
        this.logout = () => {
            Authentication.logout();
        };

        this.openAccountSettings = () => {
            let accountSettingsDialog = {
                controller: function () {
                    this.save = () => {
                        alert("saved");
                    };

                    this.close = () => {
                        $mdDialog.hide();
                    }
                    
                },
                controllerAs: '$ctrl',
                templateUrl: 'html/templates/account-settings-dialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            };
            $mdDialog.show(accountSettingsDialog);
        };
        
        this.openEditProfile = () => {
            
        }
    }

});


