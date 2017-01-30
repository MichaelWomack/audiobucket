/**
 * Created by michaelwomack on 1/29/17.
 */
angular.module('app').component('audioPlayer', {
    bindings: {
        track: '<'
    },
    templateUrl: 'js/components/audio-player/audio-player.html',
    controller: function ($document) {

        this.$onInit = () => {
            this.isPlaying = false;
            this.audio = new Audio();
            this.audio.src = this.track.url;
            this.audio.type = this.track.type;
        };

        this.play = () => {
            this.isPlaying = true;
            this.audio.play();
        };

        this.pause = () => {
            this.isPlaying = false;
            this.audio.pause();
        };

        this.forwardTenSeconds = () => {
            this.audio.currentTime += 10;
        };

        this.replayTenSeconds = () => {
            this.audio.currentTime -= 10;
        };
    }
});