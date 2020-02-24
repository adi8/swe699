#!/usr/bin/env sh
# Stop order service
order_app_id=$(ps -ef | grep java.*order.* | grep -v grep | awk '{printf $2}')
kill -9 $order_app_id

# Stop composite service
composite_app_id=$(ps -ef | grep java.*composite.* | grep -v grep | awk '{printf $2}')
kill -9 $composite_app_id

