#!/bin/bash

# Local database update,
#    $ update.sh

#
# stop on first error
set -e
DB_HOST=localhost
DB_PORT=5434
DB_USER=root
DB_PASS=root

# set pgsql password as env variable
export PGPASSWORD=$DB_PASS

# LIQUIBASE_WAIT_FOR_HOST is not empty, then wait for DB to be available
if [[ -n "$LIQUIBASE_WAIT_FOR_HOST"  ]]; then
    echo " ::: Waiting for database connection...  "
    x=1
    while [[ ! $(pg_isready --dbname=trade --host=$DB_HOST --port=$DB_PORT --username=$DB_USER) ]] || [ ! $x -le 6 ]; do
        sleep 5
        echo "Has been waiting for " $(( 5 * x++ )) " secs.."
    done
fi

if [ "$1" == "--dropAll" ]; then
    shift
    echo "Do you really want to Drop all Schemas? (Enter to confirm, CTRL-C to abort)"
    read confirm

  echo -e "\n ::: Dropping all the databases "
    dropdb --host=$DB_HOST --port=$DB_PORT --username=$DB_USER --force --if-exists trade
    echo "All schemas dropped."

  echo -e "\n ::: Recreating default databases"
  createdb --host=$DB_HOST --port=$DB_PORT --username=$DB_USER trade
fi

CMD=update
if [ ! "$1" = "" ]; then
    CMD=$1
fi

DB_HOST_PORT=$DB_HOST:$DB_PORT

# get location of this script's directory
SCRIPTPATH="$(
  cd "$(dirname "$0")"
  pwd -P
)"

CLASSPATH=
for each in $SCRIPTPATH/libs/*.jar; do
    CLASSPATH=$CLASSPATH:$each
done

echo -e "\n ::: $CMD on schema trade:::"
cd $SCRIPTPATH/schema/trade
java -cp $CLASSPATH \
     liquibase.integration.commandline.Main \
     --logLevel=warning \
     --changeLogFile=db.changelog.xml \
     --url="jdbc:postgresql://${DB_HOST_PORT}/trade" \
     --username=$DB_USER \
     --password=$DB_PASS \
     $CMD

#echo -e "\n ::: $CMD on schema eventuate:::"
#cd $SCRIPTPATH/schema/eventuate
#java -cp $CLASSPATH \
#     liquibase.integration.commandline.Main \
#     --logLevel=warning \
#     --changeLogFile=db.changelog.xml \
#     --url="jdbc:postgresql://${DB_HOST_PORT}/eventuate" \
#     --username=$DB_USER \
#     --password=$DB_PASS \
#     $CMD

 echo -e "\n ::: $CMD on schema cryptocurrency:::"
 cd $SCRIPTPATH/schema/cryptocurrency
 java -cp $CLASSPATH \
      liquibase.integration.commandline.Main \
      --logLevel=warning \
      --changeLogFile=db.changelog.xml \
      --url="jdbc:postgresql://${DB_HOST_PORT}/cryptocurrency" \
      --username=$DB_USER \
      --password=$DB_PASS \
      $CMD