#!/bin/bash

DATABASE_NAME=${DATABASE_NAME:-jayground}
POSTGRES_USER=${POSTGRES_USER:-postgres}

# echo "CREATE DATABASE $DATABASE_NAME WITH OWNER $POSTGRES_USER" | gosu postgres postgres --single 
echo "CREATE SCHEMA $DATABASE_NAME" | gosu postgres postgres --single $DATABASE_NAME
