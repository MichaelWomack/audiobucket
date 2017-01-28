/**
 * Created by michaelwomack on 1/27/17.
 */

angular.module('app').component('tracksForm', {
    templateUrl: 'html/templates/tracks-form.html',
    controller: function () {
        this.hey = () => {
            alert("what up.");
        }
    }
});