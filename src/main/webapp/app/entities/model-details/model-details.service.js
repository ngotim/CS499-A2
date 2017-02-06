(function() {
    'use strict';
    angular
        .module('assignment2App')
        .factory('ModelDetails', ModelDetails);

    ModelDetails.$inject = ['$resource'];

    function ModelDetails ($resource) {
        var resourceUrl =  'api/model-details/:id';

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
