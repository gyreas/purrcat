#!/usr/bin/bash

gjfmt=
if [[ "$CI" ]]; then
   # TODO: cache this ?
   wget "https://github.com/google/google-java-format/releases/download/v1.19.1/google-java-format-1.19.1-all-deps.jar"

   gjfmt="google-java-format-1.19.1-all-deps.jar"
else
   gjfmt="$HOME/Downloads/google-java-format-1.19.1-all-deps.jar"
fi

set -xue

java                \
   -jar "$gjfmt"    \
   --aosp           \
   --replace        \
   $(find test/ src/ -regex ".*\.java")
