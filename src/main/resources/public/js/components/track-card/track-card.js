/**
 * Created by michaelwomack on 1/31/17.
 */
angular.module('app').component('trackCard', {
    bindings: {
        track: '<'
    },
    templateUrl: 'js/components/track-card/track-card.html',
    controller: function () {
        alert(JSON.stringify(this.track));
        
        this.$onInit = () => {
            this.isFavorite = false;
            this.isPlaying = false;
        };
        
        this.toggleFavorite = () => {
          this.isFavorite = !this.isFavorite;  
        };
        
        this.togglePlay = () => {
            this.isPlaying = !this.isPlaying;
        };
    }
});