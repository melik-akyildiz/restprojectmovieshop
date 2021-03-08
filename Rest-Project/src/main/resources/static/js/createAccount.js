let headers = new Headers({'Content-Type': 'application/json'});
var app = angular.module('app', []);
app.controller('addAccount', function ($scope, $window, $http){

    $scope.recordAccount = function () {

        var data = JSON.stringify($("#add_form").formToJson());
        console.log(data);
        $http.post('http://localhost:8080/api/accounts', data, headers).then(function (response) {
            console.log("Register Button Clicked");
            if (response.data == true) {
                showMessage('Account Added Successfully', null,
                    "Ok", function() {
                        window.location.href = "/api/web/index";
                    });
            } else {
                console.log("Error");
                showMessage('UnExpected Error Occurred', null,
                    "Ok", null);
            }
        }, function (response) {
            console.log("UnExpected Error");
        });


    }


});


