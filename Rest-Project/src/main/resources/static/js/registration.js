let headers = new Headers({'Content-Type': 'application/json'});
var app = angular.module('app', []);
app.controller('registration', function ($scope, $window, $http){

    $scope.register = function () {

        var data = JSON.stringify($("#registration_form").formToJson());
        console.log(data);
        $http.post('http://localhost:8080/api/users', data, headers).then(function (response) {
            console.log("Register Button Clicked");
            if (response.data == true) {
                showMessage('Registration Successfully Completed', null,
                    "Ok", function() {
                        window.location.href = "/login";
                    });
            } else {
                console.log("Error");
                showMessage('UnExpected Error Occured', null,
                    "Ok", null);
            }
        }, function (response) {
            console.log("UnExpected Error");
        });


    }


});


