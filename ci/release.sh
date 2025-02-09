#!/usr/bin/bash

set -xue

CI=1 make package

PROPS="$(find src -regex .*\.properties)"
VERSION="$(grep -E '[0-9]+\.[0-9]+\.[0-9]+(-SNAPSHOT)?' "$PROPS" | cut -d: -f2 | cut -c 2-)"

# TODO: reconsider this
# VERSIONREL="$(echo $VERSION | cut -d- -f1)"
# SNAPSHOT="$(echo $VERSION | cut -d- -f2)"

gh release create "purrcat-v$VERSION" \
   --generate-notes                   \
   target/purrcat-$VERSION*.jar       \
   target/purrcat
