/**
 * Created by michaelwomack on 1/27/17.
 */

angular.module('app').component('tracksForm', {
    bindings: {
      albums: '<'  
    },
    templateUrl: 'js/components/tracks-form/tracks-form.html',
    controller: function () {
        this.hey = () => {
            alert("what up.");
        };

        this.showFile = () => {
            let file = document.getElementById('track-upload').files[0];
            this.fileName = file.name;
            alert(JSON.stringify(file));
            console.log(file);
        }
    }
});