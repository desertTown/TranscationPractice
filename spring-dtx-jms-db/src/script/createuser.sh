#!/usr/bin/env bash

curl -X POST -d '{"userName": "evan", "deposit": 10}' -H 'Content-Type: application/json' http://localhost:8080/api/customer

curl -X POST -d '{"userName": "evan error1", "deposit": 10}' -H 'Content-Type: application/json' http://localhost:8080/api/customer

curl -X POST -d '{"userName": "evan error2", "deposit": 10}' -H 'Content-Type: application/json' http://localhost:8080/api/customer


curl -X POST  -H 'Content-Type: application/json' http://localhost:8080/api/customer/msg?msg=new_customer


curl -X POST  -H 'Content-Type: application/json' http://localhost:8080/api/customer/msg?msg=new_customer_error1


curl -X POST  -H 'Content-Type: application/json' http://localhost:8080/api/customer/msg?msg=new_customer_error2





curl http://localhost:8080/api/customer/msg