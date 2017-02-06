(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('VehicleFuelTypeDetailController', VehicleFuelTypeDetailController);

    VehicleFuelTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VehicleFuelType'];

    function VehicleFuelTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, VehicleFuelType) {
        var vm = this;

        vm.vehicleFuelType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:vehicleFuelTypeUpdate', function(event, result) {
            vm.vehicleFuelType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
