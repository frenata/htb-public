#!/bin/bash

hash=`backup | tail -n 1 | awk '{print $2}'`
ln -s $1 $hash
backup

