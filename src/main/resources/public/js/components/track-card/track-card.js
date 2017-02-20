/**
 * Created by michaelwomack on 1/31/17.
 */
angular.module('app').component('trackCard', {
    bindings: {
        track: '<',
        artist: '<',
        albums: '<'
    },
    templateUrl: 'js/components/track-card/track-card.html',
    controller: function () {
        
        this.$onInit = () => {
            this.isFavorite = false;
            this.isPlaying = false;
            this.albumMap = new Map();
            for (let album of this.albums) {
                this.albumMap.set(album.id, album);
            }
        };
        
        this.toggleFavorite = () => {
          this.isFavorite = !this.isFavorite;  
        };
        
        this.togglePlay = () => {
            this.isPlaying = !this.isPlaying;
        };
        
        this.getAlbumName = (albumId) => {
            return this.albumMap.get(albumId).name;
        }
    }
});