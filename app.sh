CP="$(pwd)/target/classes"

java -ea -cp $CP aadesaed.cat.app.App "$@"

# if [ ! $TEST ]; then
#   n=$(fd . -e jar target/ | wc -l)
#   if [[ $n > 1 ]]; then 
#     echo "More than one jars, run manually"
#   else
#     jar=$(fd . -e jar "target/")
#     java -jar $jar "$@"
#   fi
# else
# fi
