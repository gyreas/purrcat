#!/usr/bin/bash

# set -xue

CI=1 make package

git show
 
gh release list

VERSION="$(git tag --sort -taggerdate | head -n1 | cut -c 2-)"

echo $VERSION

gh release create "v$VERSION" \
   --generate-notes           \
  target/purrcat-$VERSION.jar \
  target/purrcat
