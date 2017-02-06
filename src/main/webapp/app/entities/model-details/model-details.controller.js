(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('ModelDetailsController', ModelDetailsController);

    ModelDetailsController.$inject = ['$scope', '$state', 'ModelDetails'];

    function ModelDetailsController ($scope, $state, ModelDetails) {
        var vm = this;

        vm.modelDetails = [];

        loadAll();

        function loadAll() {
            ModelDetails.query(function(result) {
                vm.modelDetails = result;
                vm.searchQuery = null;
            });
        }
    }
})();
