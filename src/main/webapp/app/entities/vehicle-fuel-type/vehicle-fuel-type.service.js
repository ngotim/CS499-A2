(function() {
    'use strict';
    angular
        .module('assignment2App')
        .factory('VehicleFuelType', VehicleFuelType);

    VehicleFuelType.$inject = ['$resource'];

    function VehicleFuelType ($resource) {
        var resourceUrl =  'api/vehicle-fuel-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
