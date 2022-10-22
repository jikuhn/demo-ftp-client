#!/usr/bin/env bash

filename=`date +%Y%d%m_%H%M%S`-$RANDOM.txt
filesize=${RANDOM}00

declare -A users
users[test]=test
users[hokus]=123456
users[pokus]=123456
users[phantom]=opera

user=$1

if [ -z "$user" ]; then
  echo "no user specified"
  exit 1
fi

pass=${users[$user]}

if [ -z "$pass" ]; then
  echo "no user found"
  exit 2
fi

echo "user: $user"
echo "file: $filename, size: $filesize"

dd bs=1 count=$filesize if=/dev/random | curl -T- ftp://$user:$pass@localhost:21/$filename
