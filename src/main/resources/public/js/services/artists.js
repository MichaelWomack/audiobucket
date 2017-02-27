/**
 * Created by michaelwomack on 2/19/17.
 */

angular.module('app').factory('Artists', function ($http) {
    let self = {};

    self.getById = (artistId) => {
        return $http.get(`/api/artists/id/${artistId}`);
    };

    self.upsert = (artistFormData) => {
        return $http.put(`/api/artists`, artistFormData, {
            headers:{'Content-Type': undefined},
            transformRequest: angular.identity
        });
    };

    return self;
});