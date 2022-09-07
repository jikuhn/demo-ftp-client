#!/usr/bin/env bash

filename=`date +%Y%d%m_%H%M%S`-$RANDOM.txt
filesize=${RANDOM}00

declare -A users
users[test]=test
users[hokus]=abcdef
users[pokus]=efgh

declare -A servers
servers[test]=ftp1.hub.solwee.com:2101
servers[hokus]=ftp2.hub.solwee.com:2102
servers[pokus]=ftp2.hub.solwee.com:2102

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

server=${servers[$user]}

echo "user: $user"
echo "file: $filename, size: $filesize"
echo "server: $server"

echo curl -T- ftp://$user:$pass@$server/ftp/$user/$filename
dd bs=1 count=$filesize if=/dev/random | curl -v -T- ftp://$user:$pass@$server/$filename
