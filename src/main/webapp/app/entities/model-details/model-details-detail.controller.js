(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ModelDetailsDetailController', ModelDetailsDetailController);

    ModelDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ModelDetails', 'Model', 'VehicleFuelType'];

    function ModelDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, ModelDetails, Model, VehicleFuelType) {
        var vm = this;

        vm.modelDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:modelDetailsUpdate', function(event, result) {
            vm.modelDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
