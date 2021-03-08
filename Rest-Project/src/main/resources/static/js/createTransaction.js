let headers = new Headers({'Content-Type': 'application/json'});
var app = angular.module('appupdate', []);
app.controller('updatedata', function ($scope, $window, $http){

    $scope.recordRent = function () {

        var data = JSON.stringify($("#rent_product_form").formToJson());
        console.log(data);
        $http.post('http://localhost:8080/api/transaction', data, headers).then(function (response) {
            console.log("Register Button Clicked");
            if (response.data == true) {
                showMessage('Rent Successfully Completed', null,
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

