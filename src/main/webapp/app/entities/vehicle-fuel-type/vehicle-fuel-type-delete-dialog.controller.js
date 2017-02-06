(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('VehicleFuelTypeDeleteController',VehicleFuelTypeDeleteController);

    VehicleFuelTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'VehicleFuelType'];

    function VehicleFuelTypeDeleteController($uibModalInstance, entity, VehicleFuelType) {
        var vm = this;

        vm.vehicleFuelType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VehicleFuelType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
