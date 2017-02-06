(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('model-details', {
            parent: 'entity',
            url: '/model-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.modelDetails.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/model-details/model-details.html',
                    controller: 'ModelDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('modelDetails');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('model-details-detail', {
            parent: 'model-details',
            url: '/model-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.modelDetails.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/model-details/model-details-detail.html',
                    controller: 'ModelDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('modelDetails');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ModelDetails', function($stateParams, ModelDetails) {
                    return ModelDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'model-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('model-details-detail.edit', {
            parent: 'model-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model-details/model-details-dialog.html',
                    controller: 'ModelDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModelDetails', function(ModelDetails) {
                            return ModelDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('model-details.new', {
            parent: 'model-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model-details/model-details-dialog.html',
                    controller: 'ModelDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                bodyStyle: null,
                                color: null,
                                mpg: null,
                                currMiles: null,
                                price: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('model-details', null, { reload: 'model-details' });
                }, function() {
                    $state.go('model-details');
                });
            }]
        })
        .state('model-details.edit', {
            parent: 'model-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model-details/model-details-dialog.html',
                    controller: 'ModelDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModelDetails', function(ModelDetails) {
                            return ModelDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('model-details', null, { reload: 'model-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('model-details.delete', {
            parent: 'model-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model-details/model-details-delete-dialog.html',
                    controller: 'ModelDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ModelDetails', function(ModelDetails) {
                            return ModelDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('model-details', null, { reload: 'model-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
