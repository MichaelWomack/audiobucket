/**
 * Created by michaelwomack on 1/30/17.
 */

angular.module('app').filter('secondsToTimeFormat', function() {
    return function(seconds) {
        return  new Date(1970, 0, 1).setSeconds(seconds);
    }
});