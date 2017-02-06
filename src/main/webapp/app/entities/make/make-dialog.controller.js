(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('MakeDialogController', MakeDialogController);

    MakeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Make', 'Model'];

    function MakeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Make, Model) {
        var vm = this;

        vm.make = entity;
        vm.clear = clear;
        vm.save = save;
        vm.models = Model.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.make.id !== null) {
                Make.update(vm.make, onSaveSuccess, onSaveError);
            } else {
                Make.save(vm.make, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('assignment2App:makeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
