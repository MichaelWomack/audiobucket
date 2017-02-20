/**
 * Created by michaelwomack on 1/21/17.
 */

angular.module('app').controller('ProfileController', function (Authentication, Artists) {

    Authentication.getIdentity().then((response) => {
        this.user = response.data.user;
        console.log(this.user);

        Artists.getById(this.user.artistId).success((response) => {
            console.log(response);
            this.artist = response.artist;
        });
    });

    this.logout = () => {
        Authentication.logout();
    }
});