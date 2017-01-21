/**
 * Created by michaelwomack on 1/21/17.
 */

angular.module('app').component('ProfileDashboard', {
    templateUrl: 'html/views/profile.html',
    controller: function(Authentication) {
        
        this.logout = () => {
            alert("Logging out");
            //Authentication.logout();
        }
    }
});