/**
 * Created by michaelwomack on 2/19/17.
 */
angular.module('app').factory('Tracks', function ($http) {
    let self = {};
    
    self.getTrackById = (id) => {
        return $http.get(`/api/tracks/id/${id}`);
    };
    
    self.getTracksByArtistId = (artistId) => {
        return $http.get(`/api/tracks/artist_id/${artistId}`); 
    };

    self.addTrack = (track) => {
        return $http.post(`/api/tracks`, track);
    };

    return self;
});