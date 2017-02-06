(function() {
    'use strict';

    angular
        .module('assignment2App')
        .controller('MakeDetailController', MakeDetailController);

    MakeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Make', 'Model'];

    function MakeDetailController($scope, $rootScope, $stateParams, previousState, entity, Make, Model) {
        var vm = this;

        vm.make = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('assignment2App:makeUpdate', function(event, result) {
            vm.make = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
