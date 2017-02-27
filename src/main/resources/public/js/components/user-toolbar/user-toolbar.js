/**
 * Created by michaelwomack on 1/23/17.
 */

angular.module('app').component('userToolbar', {
    bindings: {
        user: '<',
        artist: '<'
    },
    require: {
        profileCtrl: '^profile'
    },
    templateUrl: 'js/components/user-toolbar/user-toolbar.html',
    controller: function (Authentication, Artists, $mdDialog) {

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
            let currentArtistInfo = this.artist;
            let profileCtrl = this.profileCtrl;
            let editProfileDialog = {
                controller: function () {
                    this.artistInfo = currentArtistInfo;

                    this.close = () => {
                        $mdDialog.hide();
                    };

                    this.apply = () => {
                        let formData = new FormData();
                        formData.append("profileImage", this.file);
                        Object.keys(this.artistInfo)
                            .forEach((key) => formData.append(key, this.artistInfo[key]));

                        Artists.upsert(formData).then((response) => {
                            alert(JSON.stringify(response));
                        });
                        // Artists.upsert(this.artistInfo).then((response) => {
                        //     let data = response.config.data;
                        //     alert(JSON.stringify(response));
                        //     alert(JSON.stringify(data));
                        //     profileCtrl.updateData();
                        // });
                    };

                    this.setFile = (fileInput) => {
                        this.file = fileInput.files[0];
                    };
                }
                ,
                controllerAs: '$ctrl',
                templateUrl: 'html/templates/edit-profile-dialog.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            };

            $mdDialog.show(editProfileDialog);
        }
    }

});


