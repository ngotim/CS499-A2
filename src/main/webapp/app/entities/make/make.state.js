(function() {
    'use strict';

    angular
        .module('assignment2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('make', {
            parent: 'entity',
            url: '/make',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.make.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/make/makes.html',
                    controller: 'MakeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('make');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('make-detail', {
            parent: 'make',
            url: '/make/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'assignment2App.make.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/make/make-detail.html',
                    controller: 'MakeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('make');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Make', function($stateParams, Make) {
                    return Make.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'make',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('make-detail.edit', {
            parent: 'make-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/make/make-dialog.html',
                    controller: 'MakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Make', function(Make) {
                            return Make.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('make.new', {
            parent: 'make',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/make/make-dialog.html',
                    controller: 'MakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                make: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('make', null, { reload: 'make' });
                }, function() {
                    $state.go('make');
                });
            }]
        })
        .state('make.edit', {
            parent: 'make',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/make/make-dialog.html',
                    controller: 'MakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Make', function(Make) {
                            return Make.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('make', null, { reload: 'make' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('make.delete', {
            parent: 'make',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/make/make-delete-dialog.html',
                    controller: 'MakeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Make', function(Make) {
                            return Make.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('make', null, { reload: 'make' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
