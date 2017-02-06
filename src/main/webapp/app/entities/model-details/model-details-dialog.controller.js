(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ModelDetailsDialogController', ModelDetailsDialogController);

    ModelDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ModelDetails', 'Model', 'VehicleFuelType'];

    function ModelDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ModelDetails, Model, VehicleFuelType) {
        var vm = this;

        vm.modelDetails = entity;
        vm.clear = clear;
        vm.save = save;
        vm.models = Model.query();
        vm.vehiclefueltypes = VehicleFuelType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.modelDetails.id !== null) {
                ModelDetails.update(vm.modelDetails, onSaveSuccess, onSaveError);
            } else {
                ModelDetails.save(vm.modelDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:modelDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
