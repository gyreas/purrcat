#!/usr/bin/env -S bash

java                                                             \
   -jar "$HOME/Downloads/google-java-format-1.19.1-all-deps.jar" \
   --aosp                                                        \
   --replace                                                     \
   $(find test/ src/ -regex ".*\.java")
