CP="target/classes"

java \
  -ea \
  -cp $CP \
  -agentpath:/home/saed/Downloads/visualvm_217/visualvm_217/visualvm/lib/deployed/jdk16/linux-amd64/libprofilerinterface.so=/home/saed/Downloads/visualvm_217/visualvm_217/visualvm/lib,5140 \
  aadesaed.cat.app.App "$@"
