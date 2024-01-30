#!/bin/sh
if [ "$#" -eq 3 ]; then
python sbp.py "$1" "$2" "$3"
elif [ "$#" -eq 2 ]; then
python sbp.py "$1" "$2"
else
python sbp.py "$1"
fi