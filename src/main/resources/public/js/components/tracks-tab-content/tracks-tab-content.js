/**
 * Created by michaelwomack on 1/31/17.
 */

angular.module('app').component('tracksTabContent', {
    bindings: {
        artist: '<'
    },
    templateUrl: 'js/components/tracks-tab-content/tracks-tab-content.html',
    controller: function (Tracks) {

        /* Using ng-if on tracks-tab-content element to wait for 'artist' binding to resolve */
        this.$onInit = () => {
            this.updateData();
        };

        this.updateData = () => {
            Tracks.getTracksByArtistId(this.artist.id).then((response) => {
                let res = response.data;
                this.tracks = res.data;
                this.albums = [{name: 'Album of the Year', id: 1}];
                this.showTrackForm = !this.albums;

            });
        };


        this.toggleTrackForm = () => {
            this.showTrackForm = !this.showTrackForm;
        };


    }
});