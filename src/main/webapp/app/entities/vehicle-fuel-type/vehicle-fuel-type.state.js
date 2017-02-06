(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vehicle-fuel-type', {
            parent: 'entity',
            url: '/vehicle-fuel-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.vehicleFuelType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vehicle-fuel-type/vehicle-fuel-types.html',
                    controller: 'VehicleFuelTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vehicleFuelType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('vehicle-fuel-type-detail', {
            parent: 'vehicle-fuel-type',
            url: '/vehicle-fuel-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.vehicleFuelType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vehicle-fuel-type/vehicle-fuel-type-detail.html',
                    controller: 'VehicleFuelTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vehicleFuelType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VehicleFuelType', function($stateParams, VehicleFuelType) {
                    return VehicleFuelType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vehicle-fuel-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('vehicle-fuel-type-detail.edit', {
            parent: 'vehicle-fuel-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vehicle-fuel-type/vehicle-fuel-type-dialog.html',
                    controller: 'VehicleFuelTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VehicleFuelType', function(VehicleFuelType) {
                            return VehicleFuelType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vehicle-fuel-type.new', {
            parent: 'vehicle-fuel-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vehicle-fuel-type/vehicle-fuel-type-dialog.html',
                    controller: 'VehicleFuelTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fuelDesc: null,
                                postalCode: null,
                                city: null,
                                stateProvince: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vehicle-fuel-type', null, { reload: 'vehicle-fuel-type' });
                }, function() {
                    $state.go('vehicle-fuel-type');
                });
            }]
        })
        .state('vehicle-fuel-type.edit', {
            parent: 'vehicle-fuel-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vehicle-fuel-type/vehicle-fuel-type-dialog.html',
                    controller: 'VehicleFuelTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VehicleFuelType', function(VehicleFuelType) {
                            return VehicleFuelType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vehicle-fuel-type', null, { reload: 'vehicle-fuel-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vehicle-fuel-type.delete', {
            parent: 'vehicle-fuel-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vehicle-fuel-type/vehicle-fuel-type-delete-dialog.html',
                    controller: 'VehicleFuelTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VehicleFuelType', function(VehicleFuelType) {
                            return VehicleFuelType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vehicle-fuel-type', null, { reload: 'vehicle-fuel-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
