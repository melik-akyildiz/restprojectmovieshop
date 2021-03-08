let headers = new Headers({'Content-Type': 'application/json'});
var app = angular.module('app', []);
app.controller('controller', function ($scope, $window, $http) {
    $scope.products = [];
    $scope.productGenres = [];
    $scope.transaction = [];
    $scope.getAllCC = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/products'
        }).then(function (success) {
            $scope.products = success.data;

        }, function (error) {

        });
    }

    $scope.getAllTransaction = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/transaction'
        }).then(function (success) {
            $scope.transaction = success.data;

        }, function (error) {

        });
    }

    $scope.loadComboBox = function () {

        $http({
            method: 'GET',
            url: 'http://localhost:8080/api/products/comboboxdata'
        }).then(function (success) {
            $scope.productGenres = success.data;

        }, function (error) {

        });
    }

    $scope.filterProducts = function (types) {
        if (types != null) {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/api/products/distinctproduct/' + types
            }).then(function (success) {
                $scope.products = success.data;

            }, function (error) {

            });
        }
    }

    $scope.search = function () {
        if ($scope.searchText != null) {
            $http({
                method: 'GET',
                url: 'http://localhost:8080/api/products/search/' + $scope.searchText
            }).then(function (success) {
                $scope.products = success.data;

            }, function (error) {

            });
        }

    }


    $scope.rentMovie = function () {
        var productsId = [];
        for (var i = 0; i < $scope.products.length; i++) {
            if ($scope.products[i].Selected) {
                productsId.push($scope.products[i].id);
            }
        }

        if (productsId.length != null) {
  
        window.open("Rent Movie", "update");

        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", "http://localhost:8080/api/web/rentProduct");
        form.setAttribute("target", "update");

        var hiddenField1 = document.createElement("input");
        hiddenField1.setAttribute("type", "hidden");
        hiddenField1.setAttribute("id", "productId");
        hiddenField1.setAttribute("name", "productId");
        hiddenField1.setAttribute("value", productsId);
        form.appendChild(hiddenField1);

        document.body.appendChild(form);
        form.submit();
    }else{
            showMessage('You have to choose at least one movie', null,
                "Ok", null);
        }
}

    $scope.rentBackMovie = function (transactionId) {


        if (transactionId != null) {

            window.open("Rent Movie", "update");

            var form = document.createElement("form");
            form.setAttribute("method", "post");
            form.setAttribute("action", "http://localhost:8080/api/web/rentBackProduct");
            form.setAttribute("target", "update");

            var hiddenField1 = document.createElement("input");
            hiddenField1.setAttribute("type", "hidden");
            hiddenField1.setAttribute("id", "transactionId");
            hiddenField1.setAttribute("name", "transactionId");
            hiddenField1.setAttribute("value", transactionId);
            form.appendChild(hiddenField1);

            document.body.appendChild(form);
            form.submit();
        }else{
            showMessage('You have to choose at least one transaction', null,
                "Ok", null);
        }
    }

$scope.selectProduct = function (productId, userId) {
    angular.forEach($scope.products, function (item) {
        if (item.id != productId) {
           // item.Selected = false;
        }
    });
}


})
;


