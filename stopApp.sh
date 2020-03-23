#!/usr/bin/env sh
# Stop order service
order_app_id=$(ps -ef | grep java.*order.* | grep -v grep | awk '{printf $2}')
if [ "x$order_app_id" = "x" ]; then
  echo "Order service not running"
else
  kill -9 $order_app_id
fi

# Stop composite service
composite_app_id=$(ps -ef | grep java.*composite.* | grep -v grep | awk '{printf $2}')
if [ "x$composite_app_id" = "x" ]; then
  echo "Composite service not running"
else
  kill -9 $composite_app_id
fi

