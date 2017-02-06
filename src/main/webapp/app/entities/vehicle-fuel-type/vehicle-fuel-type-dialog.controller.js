(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('VehicleFuelTypeDialogController', VehicleFuelTypeDialogController);

    VehicleFuelTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VehicleFuelType'];

    function VehicleFuelTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VehicleFuelType) {
        var vm = this;

        vm.vehicleFuelType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vehicleFuelType.id !== null) {
                VehicleFuelType.update(vm.vehicleFuelType, onSaveSuccess, onSaveError);
            } else {
                VehicleFuelType.save(vm.vehicleFuelType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:vehicleFuelTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
