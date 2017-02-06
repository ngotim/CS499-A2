(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('MakeDeleteController',MakeDeleteController);

    MakeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Make'];

    function MakeDeleteController($uibModalInstance, entity, Make) {
        var vm = this;

        vm.make = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Make.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
