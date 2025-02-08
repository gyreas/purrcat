#!/usr/bin/env -S bash

java \
   -Djava.library.path=target/ \
   -ea -cp "$(pwd)/target/classes" \
   aadesaed.cat.app.App "$@"
