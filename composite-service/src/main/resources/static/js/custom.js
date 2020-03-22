$('body').on('click', 'button[name=addCartBtn]', function() {
  var itemCard = $(this).parent('div');
  var itemName = itemCard.find('h5').text();
  var itemPrice = itemCard.find('h6').text();
  var shoppingCartList = $('#shoppingCart').find('ul');
  var newCartItem = $('#cartItemSample').clone();
  newCartItem.find('span.font-italic').append(itemName)
  newCartItem.find('span.font-weight-light').append(itemPrice)
  shoppingCartList.append(newCartItem).show();
  newCartItem.show()
});

$('body').on('click', 'button[name=removeCartBtn]', function() {
  $(this).parent('li').remove();
});


