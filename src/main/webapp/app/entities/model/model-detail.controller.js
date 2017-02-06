(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ModelDetailController', ModelDetailController);

    ModelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Model', 'Make', 'ModelDetails'];

    function ModelDetailController($scope, $rootScope, $stateParams, previousState, entity, Model, Make, ModelDetails) {
        var vm = this;

        vm.model = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:modelUpdate', function(event, result) {
            vm.model = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
