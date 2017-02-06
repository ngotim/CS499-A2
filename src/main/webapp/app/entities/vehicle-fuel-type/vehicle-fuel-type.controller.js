(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('VehicleFuelTypeController', VehicleFuelTypeController);

    VehicleFuelTypeController.$inject = ['$scope', '$state', 'VehicleFuelType'];

    function VehicleFuelTypeController ($scope, $state, VehicleFuelType) {
        var vm = this;

        vm.vehicleFuelTypes = [];

        loadAll();

        function loadAll() {
            VehicleFuelType.query(function(result) {
                vm.vehicleFuelTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
