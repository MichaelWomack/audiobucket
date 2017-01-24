/**
 * Created by michaelwomack on 1/23/17.
 */
angular.module('app').component('userToolbar', {
   
    templateUrl: 'html/templates/user-toolbar.html',
    controller: function(Authentication) {
        this.logout = () => {
            Authentication.logout();
        };
    }
});