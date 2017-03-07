#!/bin/bash

if [ $# -ne 1 ]; then
    echo 'usage: ./deploy.sh <version>'
    exit 1
fi

mvn appengine:deploy -Dapp.deploy.version=$1