(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('MakeController', MakeController);

    MakeController.$inject = ['$scope', '$state', 'Make'];

    function MakeController ($scope, $state, Make) {
        var vm = this;

        vm.makes = [];

        loadAll();

        function loadAll() {
            Make.query(function(result) {
                vm.makes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
