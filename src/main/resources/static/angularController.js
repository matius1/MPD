var app = angular.module('MyApp', ['ngRoute']);

app.controller('MainController', ['$scope', '$http', function ($scope, $http) {

    $scope.show = false;
    $scope.numberInOsX = 0;
    $scope.numberOfTasks = 1;
    $scope.tasks = [$scope.numberOfTasks];
    $scope.machineTimes = [];
    $scope.colors = ['#00efc0', '#b34600', '#950008', '#993c00',
        '#ff959a', '#00bc97', '#ffc199', '#ffaeb3', '#00a383', '#ffd1b3', '#ffc8cb', '#00896f',
        '#ffe0cc', '#7b0006', '#23ffd4', '#803200'];

    $scope.getTasksArray = getTasksArray;
    $scope.getJohnsonResult = getJohnsonResult;
    $scope.getNumber = getNumber;

    function getTasksArray() {
        return new Array($scope.numberOfTasks);
    }

    function getJohnsonResult() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/johnson/getJohnsonResult',
            params: {
                tasksTimes: JSON.stringify($scope.tasks)
            }
        }).success(function (response) {
            $scope.machineTimes = response;
            $scope.numberInOsX = response.slice(-1).pop();
            $scope.show = true;
        })
            .error(function (error) {
                console.log(error);
            });
    }

    $scope.$watch('numberOfTasks', function () {
        $scope.tasks = new Array($scope.numberOfTasks);

        for (var i = 0; i < $scope.numberOfTasks; i++) {
            $scope.tasks[i] = {};
        }
    });

    function getNumber(num) {
        return new Array(num);
    }
}]);

app.controller('McNaughtonController', ['$scope', '$http', function ($scope, $http) {

    $scope.show = false;
    $scope.machine = 0;
    $scope.numberOfTasks = 1;
    $scope.numberOfMachine = 1;
    $scope.Cmax = 0;
    $scope.tasks = [$scope.numberOfTasks];
    $scope.machineTimes = [];
    $scope.colors = ['#00efc0', '#b34600', '#950008', '#993c00',
        '#ff959a', '#00bc97', '#ffc199', '#ffaeb3', '#00a383', '#ffd1b3', '#ffc8cb', '#00896f',
        '#ffe0cc', '#7b0006', '#23ffd4', '#803200'];

    $scope.getTasksArray = getTasksArray;
    $scope.getMcNaughtonResult = getMcNaughtonResult;
    $scope.getNumber = getNumber;

    function getTasksArray() {
        return new Array($scope.numberOfTasks);
    }

    function getMcNaughtonResult() {
        this.machine = this.numberOfMachine;
        $http({
            method: 'GET',
            url: 'http://localhost:8080/mcnaughton/getMcNaughtonResult',
            params: {
                tasksTimes: JSON.stringify($scope.tasks),
                machineNumber: this.numberOfMachine
            }
        }).success(function (response) {
            $scope.machineTimes = response;
            $scope.Cmax = response.slice(-1).pop();
            $scope.show = true;
        })
            .error(function (error) {
                console.log(error);
            });
    }

    $scope.$watch('numberOfTasks', function () {
        $scope.tasks = new Array($scope.numberOfTasks);

        for (var i = 0; i < $scope.numberOfTasks; i++) {
            $scope.tasks[i] = {};
        }
    });

    function getNumber(num) {
        return new Array(num);
    }
}]);