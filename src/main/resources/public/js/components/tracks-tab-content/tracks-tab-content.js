/**
 * Created by michaelwomack on 1/31/17.
 */

angular.module('app').component('tracksTabContent', {
    templateUrl: 'js/components/tracks-tab-content/tracks-tab-content.html',
    controller: function () {
        
        //Request tracks content from db
        
        this.showTrackForm = false;
        
        this.toggleTrackForm = () => {
            this.showTrackForm = !this.showTrackForm;
        };
        
        
    }
});