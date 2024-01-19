#!/usr/bin/env -S bash

##
# For testing the app given the jnr dependencies, this is necessary otherwise it breaks.
## 
LIBS=$(fd . -e jar lib/)
CP="$(pwd)/target/classes"

JARS=
for jar in $LIBS; do 

  JARS="$JARS:$jar/"

done

java -ea -cp $CP:$JARS aadesaed.cat.app.App "$@"
