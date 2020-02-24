#!/usr/bin/env sh
nohup java -jar /home/ubuntu/order-1.0.0-SNAPSHOT.jar 1> /home/ubuntu/order.log 2> /home/ubuntu/order.err &
nohup java -jar /home/ubuntu/composite-service-1.0.0-SNAPSHOT.jar 1> /home/ubuntu/composite.log 2> /home/ubuntu/composite.err &
