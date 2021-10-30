#!/bin/bash

count=20000


let i=$count-1
while [ $i -ge 0 ];
do
curl -i http://7097c466-eksworkshop-ekswo-fd5c-2121886368.ap-northeast-2.elb.amazonaws.com/order
let i=i-1
# ./usleep 1000
done