#!/usr/bin/env -S bash

export NO_JAR=1

java \
   -Djava.library.path=target/ \
   -ea -cp "$(pwd)/target/classes" \
   aadesaed.cat.app.App "$@"
