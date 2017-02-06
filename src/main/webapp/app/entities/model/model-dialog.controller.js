(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ModelDialogController', ModelDialogController);

    ModelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Model', 'Make', 'ModelDetails'];

    function ModelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Model, Make, ModelDetails) {
        var vm = this;

        vm.model = entity;
        vm.clear = clear;
        vm.save = save;
        vm.makes = Make.query();
        vm.modeldetails = ModelDetails.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.model.id !== null) {
                Model.update(vm.model, onSaveSuccess, onSaveError);
            } else {
                Model.save(vm.model, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:modelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
