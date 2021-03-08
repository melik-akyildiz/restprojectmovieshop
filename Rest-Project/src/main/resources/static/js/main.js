function showMessage(title, message, addOkButton, okButtonListener) {
    _createMessage(title, message, messageDiv, addOkButton, okButtonListener);
    if (addOkButton == null) {
        $("#message_container").click(function (e) {
            _hideMessage(okButtonListener);
        });
    }
}

function _createMessage(title, message, msgDiv, addOkButton, okButtonListener) {
    _hideMessage(null, true);
    $("body").append(msgDiv);
    $("#message_container").height($(document).height());
    $("#message_title").append(title);
    $("#message_message").append(message);
    if (addOkButton != null)
        $("#message_message").append('<input type="button" id="message_okButton" class="button_color button_inline button_small" value="' + addOkButton + '">');
    if (okButtonListener)
        $("#message_okButton").on("click", okButtonListener);
    else {
        $("#message_okButton").on("click", function () {
            _hideMessage();
        });
    }
    if (message)
        $("#message_message").css("padding-top", "5px");
    $("#message").css("margin-top", $(window).height() / 2 - $("#message").height() / 2 + $(document).scrollTop());
}

function _hideMessage(callback, fast) {
    if (fast) {
        $("#message_container").remove();
        if (callback)
            callback();
        return;
    }
    $("#message_container").fadeOut(function () {
        $("#message_container").remove();
        if (callback)
            callback();
    });
}

var messageDiv =
    '<div id="message_container">' +
    '<div id="message">' +
    '<div id="message_title"></div>' +
    '<div id="message_message"></div>' +
    '<div id="message_link"></div>' +
    '</div>' +
    '</div>';

$.fn.formToJson = function () {
    form = $(this);

    var formArray = form.serializeArray();
    var jsonOutput = {};

    $.each(formArray, function (i, element) {
        var elemNameSplit = element['name'].split('[');
        var elemObjName = 'jsonOutput';

        $.each(elemNameSplit, function (nameKey, value) {
            if (nameKey != (elemNameSplit.length - 1)) {
                if (value.slice(value.length - 1) == ']') {
                    if (value === ']') {
                        elemObjName = elemObjName + '[' + Object.keys(eval(elemObjName)).length + ']';
                    } else {
                        elemObjName = elemObjName + '[' + value;
                    }
                } else {
                    elemObjName = elemObjName + '.' + value;
                }

                if (typeof eval(elemObjName) == 'undefined')
                    eval(elemObjName + ' = {};');
            } else {
                if (value.slice(value.length - 1) == ']') {
                    if (value === ']') {
                        eval(elemObjName + '[' + Object.keys(eval(elemObjName)).length + '] = \'' + element['value'].replace("'", "\\'") + '\';');
                    } else {
                        eval(elemObjName + '[' + value + ' = \'' + element['value'].replace("'", "\\'") + '\';');
                    }
                } else {
                    eval(elemObjName + '.' + value + ' = \'' + element['value'].replace("'", "\\'") + '\';');
                }
            }
        });
    });

    return jsonOutput;
}

function _setOwnerAccount(account) {
    $("#ownerAccountNumber").val(account);

}

function _onCheckBoxChange(checkbox) {
    var checkboxes = document.getElementsByName('check')
    checkboxes.forEach((item) => {
        if (item !== checkbox) item.checked = false
    })
}

function _setWithdrawCreditAccount(account) {
    $("#ownerAccountNumber").val(account);
    $("#targetAccountNumber").val(account);
    $('.check input:checkbox').click(function () {
        $('.check input:checkbox').not("#account").prop('checked', false);
    });
}

function _calculateTotalPrice(curDate, dateObj, productList) {
    var currDate = new Date(curDate);
    var rentDate = new Date(dateObj.value);
    var difference = (rentDate.getTime() - currDate.getTime()) / (1000 * 3600 * 24);
    var totalAmount = 0;

    for (var i = 0; i < productList.length; i++) {
        var productPrice = 0;
        if (difference > parseInt(productList[i].productsPrice.interval)) {
            productPrice = productList[i].productsPrice.amount +
                ((difference - parseInt(productList[i].productsPrice.interval)) *
                    productList[i].productsPrice.amount);
        } else {
            productPrice = productList[i].productsPrice.amount;
        }
        $("#" + productList[i].id).html(parseFloat(productPrice));
        totalAmount = totalAmount + productPrice;
    }
    $("#totalAmount").html(parseFloat(totalAmount));

}

function _calculateTotalPrice(curDate, dateObj, createAt, productList) {
    var currDate = new Date(curDate);
    var rentDate = new Date(dateObj.value);
    var createDate = new Date(createAt);
    var difference = (rentDate.getTime() - createDate.getTime()) / (1000 * 3600 * 24);
    var differenceCurrentDate = (rentDate.getTime() - currDate.getTime()) / (1000 * 3600 * 24);
    var totalAmount = 0;

    for (var i = 0; i < productList.length; i++) {
        var productPrice = 0;
        if (difference > parseInt(productList[i].productsPrice.interval)) {
            productPrice = productList[i].productsPrice.amount +
                ((difference - parseInt(productList[i].productsPrice.interval)) *
                    productList[i].productsPrice.amount);
        } else {
            productPrice = productList[i].productsPrice.amount;

        }
        if (differenceCurrentDate > 0) {
            productPrice = productPrice + differenceCurrentDate * productList[i].productsPrice.amount;
            var overTimePrice = parseFloat(differenceCurrentDate * productList[i].productsPrice.amount);
            $("#" + productList[i].id + "overtime").html(differenceCurrentDate);
            $("#" + productList[i].id + "overtime_price").html(overTimePrice);
        }
        $("#" + productList[i].id).html(parseFloat(productPrice));
        totalAmount = totalAmount + productPrice;
    }
    $("#totalAmount").html(parseFloat(totalAmount));

}