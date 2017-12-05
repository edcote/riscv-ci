#!/bin/bash

wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -i

echo "deb https://pkg.jenkins.io/debian-stable binary/" >> /etc/apt/sources.list

apt-get update
apt-get install jenkins

service jenkins restart

echo "http://localhost:8080"

