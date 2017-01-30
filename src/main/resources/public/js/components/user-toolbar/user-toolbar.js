/**
 * Created by michaelwomack on 1/23/17.
 */

angular.module('app').component('userToolbar', {

    templateUrl: 'js/components/user-toolbar/user-toolbar.html',
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
            let editProfileDialog = {
                controller: function() {
                    this.close = () => {
                        $mdDialog.hide();
                    };

                    this.save = () => {
                        alert('saved');
                    };
                },
                controllerAs: '$ctrl',
                templateUrl: 'html/templates/edit-profile-dialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            };
            
            $mdDialog.show(editProfileDialog);
        }
    }

});


