$('body').on('click', 'button[name=addCartBtn]', function() {
  var itemCard = $(this).parent('div')
  var itemName = itemCard.find('h5').text()
  var itemPrice = itemCard.find('h6').text()
  var itemId = itemCard.find('h5').data('menuitemid')
  var foodCartList = $("#foodCartList")
  var newCartItem = $('#cartItemSample').clone()
  newCartItem.find('span.font-italic').append(itemName)
  newCartItem.find('span.font-weight-light').append(itemPrice)
  newCartItem.attr('data-menuItemId', itemId)
  foodCartList.append(newCartItem).show()
  newCartItem.show()
  calcOrderTotal()
});

$('body').on('click', 'button[name=removeCartBtn]', function() {
  $(this).parents('li').remove()
  calcOrderTotal()
});

function calcOrderTotal() {
  var foodCartList = $('#foodCartList')
  var itemTotal = 0.0
  foodCartList.find("li").each(function(idx, e) {
    var price = parseFloat($(e).find('span.font-weight-light').text().substr(1).trim())
    if (!isNaN(price)) {
      itemTotal = itemTotal + price
    }
  });
  var itemTaxRate = 0.05
  var itemTax = itemTotal * 0.05
  var total = itemTotal + itemTax

  $('#itemTotal').text('$' + itemTotal.toFixed(2))
  $('#itemTax').text('$' + itemTax.toFixed(2))
  $('#total').text('$' + total.toFixed(2))
}

function findItem(item) {
  if (item['id'] == this['id']) {
    return true
  }
  return false
}

$('body').on('click', 'button[name=reviewOrder]', function() {
  var data = {}
  data['restaurantId'] = $("#restaurantName").data('restaurantid')
  data['orderMenuItems'] = []
  var foodCartList = $('#foodCartList')
  foodCartList.find('li').each(function(idx, e) {
    var id = $(e).data('menuitemid')
    var price = parseFloat($(e).find('span.font-weight-light').text().substr(1).trim())
    var name = $(e).find('span.font-italic').text().trim()
    if (!isNaN(price)) {
      var itemIdx = data['orderMenuItems'].findIndex(findItem, {'id': id})
      if (itemIdx != -1) {
        data['orderMenuItems'][itemIdx]['qty'] += 1
      }
      else {
        var itemObj = {}
        itemObj['id'] = id
        itemObj['name'] = name
        itemObj['price'] = price
        itemObj['qty'] = 1
        data['orderMenuItems'].push(itemObj)
      }
    }
  });
  data['baseAmount'] = parseFloat($('#itemTotal').text().substr(1).trim())
  data['tax'] = parseFloat($('#itemTax').text().substr(1).trim())
  data['amount'] = parseFloat($('#total').text().substr(1).trim())

  $.ajax({
    url: '/order/review',
    method: 'POST',
    dataType: 'html',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(data),
    success: function(data) {
      document.open()
      document.write(data)
      document.close()
      var id = $('#orderTitle').data('orderid')
      history.pushState(null, 'Order Out! Review', '/order/review?id=' + id)
    },
    error: function(data) {
      console.log("Request failed")
    }
  });
});

$('body').on('click', '#placeOrderBtn', function(e) {
  $('#placeOrderForm').submit()
});
