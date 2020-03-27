$('body').on('click', 'button[name=addCartBtn]', function() {
  var itemCard = $(this).parent('div');
  var itemName = itemCard.find('h5').text();
  var itemPrice = itemCard.find('h6').text();
  var foodCartList = $("#foodCartList");
  var newCartItem = $('#cartItemSample').clone();
  newCartItem.find('span.font-italic').append(itemName)
  newCartItem.find('span.font-weight-light').append(itemPrice)
  foodCartList.append(newCartItem).show();
  newCartItem.show();
  calcOrderTotal();
});

$('body').on('click', 'button[name=removeCartBtn]', function() {
  $(this).parents('li').remove();
  calcOrderTotal();
});

function calcOrderTotal() {
  var foodCartList = $('#foodCartList');
  var itemTotal = 0.0;
  foodCartList.find("li").each(function(idx, e) {
    var price = parseFloat($(e).find('span.font-weight-light').text().substr(1).trim());
    if (!isNaN(price)) {
      itemTotal = itemTotal + price;
    }
  });
  var itemTaxRate = 0.05;
  var itemTax = itemTotal * 0.05;
  var total = itemTotal + itemTax;

  $('#itemTotal').text('$' + itemTotal.toFixed(2));
  $('#itemTax').text('$' + itemTax.toFixed(2));
  $('#total').text('$' + total.toFixed(2));
}

