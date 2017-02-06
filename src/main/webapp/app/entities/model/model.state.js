(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('model', {
            parent: 'entity',
            url: '/model',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.model.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/model/models.html',
                    controller: 'ModelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('model');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('model-detail', {
            parent: 'model',
            url: '/model/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.model.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/model/model-detail.html',
                    controller: 'ModelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('model');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Model', function($stateParams, Model) {
                    return Model.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'model',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('model-detail.edit', {
            parent: 'model-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model/model-dialog.html',
                    controller: 'ModelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Model', function(Model) {
                            return Model.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('model.new', {
            parent: 'model',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model/model-dialog.html',
                    controller: 'ModelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                modelName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('model', null, { reload: 'model' });
                }, function() {
                    $state.go('model');
                });
            }]
        })
        .state('model.edit', {
            parent: 'model',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model/model-dialog.html',
                    controller: 'ModelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Model', function(Model) {
                            return Model.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('model', null, { reload: 'model' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('model.delete', {
            parent: 'model',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/model/model-delete-dialog.html',
                    controller: 'ModelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Model', function(Model) {
                            return Model.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('model', null, { reload: 'model' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
