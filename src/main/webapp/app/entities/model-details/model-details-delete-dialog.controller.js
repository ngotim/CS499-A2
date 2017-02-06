(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ModelDetailsDeleteController',ModelDetailsDeleteController);

    ModelDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ModelDetails'];

    function ModelDetailsDeleteController($uibModalInstance, entity, ModelDetails) {
        var vm = this;

        vm.modelDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ModelDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
