'use strict';

describe('Controller Tests', function() {

    describe('ModelDetails Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockModelDetails, MockModel, MockVehicleFuelType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockModelDetails = jasmine.createSpy('MockModelDetails');
            MockModel = jasmine.createSpy('MockModel');
            MockVehicleFuelType = jasmine.createSpy('MockVehicleFuelType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ModelDetails': MockModelDetails,
                'Model': MockModel,
                'VehicleFuelType': MockVehicleFuelType
            };
            createController = function() {
                $injector.get('$controller')("ModelDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'assignment2App:modelDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
