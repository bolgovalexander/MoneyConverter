$(document).on('change', 'select[name="currencyFond"]', function () {
    var element = $(this).find('option:selected');
    var curValue = element.attr("curValue");
    var money = $('input[name="transferAmount"]').val();
    var text = element.text();
    if (curValue) {
        var culcMoney = (Number(money) / Number(curValue)).toFixed(2);
        if(culcMoney == "NaN"){
            culcMoney='Неверный формат суммы';
        }
        var culcMoneyElement = '<p>' + culcMoney + '</p>';
        $('#currentMoneyValue').html(
            '<div class="card mb-4 shadow-sm">' +
            '<div class="card-header">' +
            '<h4 class="my-0 fw-normal">' + text + '</h4>' +
            '</div>' +
            '<div class="card-body">' +
            '<h1 class="card-title pricing-card-title my-4">Курс: ' + Number(curValue).toFixed(2) + '</h1>' +
        culcMoneyElement +
        '</div>' +
        '</div>')

    } else {
        $('#currentMoneyValue').html('');
    }
});

$(document).on('input', 'input[name="transferAmount"]', function () {
    var element = $('select[name="currencyFond"]').find('option:selected');
    var curValue = element.attr("curValue");
    var money = $(this).val();
    var text = element.text();
    if (curValue) {
        var culcMoney = (Number(money) / Number(curValue)).toFixed(2);
        if(culcMoney == "NaN"){
            culcMoney='Неверный формат суммы';
        }
        var culcMoneyElement = '<p>' + culcMoney + '</p>';
        $('#currentMoneyValue').html(
            '<div class="card mb-4 shadow-sm">' +
            '<div class="card-header">' +
            '<h4 class="my-0 fw-normal">' + text + '</h4>' +
            '</div>' +
            '<div class="card-body">' +
            '<h1 class="card-title pricing-card-title my-4">Курс: ' + Number(curValue).toFixed(2) + '</h1>' +
            culcMoneyElement +
            '</div>' +
            '</div>')

    } else {
        $('#currentMoneyValue').html('');
    }
});