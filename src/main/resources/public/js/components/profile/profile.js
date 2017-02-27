/**
 * Created by michaelwomack on 2/19/17.
 */
angular.module('app').component('profile', {
    templateUrl: 'js/components/profile/profile.html',
    controller: function (Authentication, Artists) {
        
        this.$onInit = () => {
            this.updateData();
        };
        
        this.updateData = () => {
            Authentication.getIdentity().then((response) => {
                this.user = response.data.user;
                console.log(this.user);

                Artists.getById(this.user.artistId).success((response) => {
                    //console.log(response);
                    this.artist = response.artist;
                    console.log(this.artist);
                });
            });
        };

        this.logout = () => {
            Authentication.logout();
        };
    }
});