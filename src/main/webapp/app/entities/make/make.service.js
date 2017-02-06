(function() {
    'use strict';
    angular
        .module('assignment2App')
        .factory('Make', Make);

    Make.$inject = ['$resource'];

    function Make ($resource) {
        var resourceUrl =  'api/makes/:id';

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
