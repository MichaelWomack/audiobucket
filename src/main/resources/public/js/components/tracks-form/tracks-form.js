/**
 * Created by michaelwomack on 1/27/17.
 */

angular.module('app').component('tracksForm', {
    templateUrl: 'js/components/tracks-form.html',
    controller: function () {
        this.hey = () => {
            alert("what up.");
        }
    }
});