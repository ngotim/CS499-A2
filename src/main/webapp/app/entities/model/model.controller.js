(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ModelController', ModelController);

    ModelController.$inject = ['$scope', '$state', 'Model'];

    function ModelController ($scope, $state, Model) {
        var vm = this;

        vm.models = [];

        loadAll();

        function loadAll() {
            Model.query(function(result) {
                vm.models = result;
                vm.searchQuery = null;
            });
        }
    }
})();
