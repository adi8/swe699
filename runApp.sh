#!/usr/bin/env sh
nohup java -jar /home/ubuntu/api-1.0.0-SNAPSHOT.jar &
nohup java -jar /home/ubuntu/order-1.0.0-SNAPSHOT.jar &
nohup java -jar /home/ubuntu/composite-service-1.0.0-SNAPSHOT.jar &
