PROJECTDIR="$HOME/learnings/java/purrcat"
CP="$PROJECTDIR/target/classes"

java \
  -ea \
  -cp $CP \
  -agentpath:/home/saed/Downloads/YourKit-JavaProfiler-2023.9-b103-x64/YourKit-JavaProfiler-2023.9/bin/linux-x86-64/libyjpagent.so=disablestacktelemetry,exceptions=disable,delay=10000 \
  -agentpath:/home/saed/jprofiler14/bin/linux-x64/libjprofilerti.so=port=8849\
  -agentpath:/home/saed/Downloads/visualvm_217/visualvm_217/visualvm/lib/deployed/jdk16/linux-amd64/libprofilerinterface.so=/home/saed/Downloads/visualvm_217/visualvm_217/visualvm/lib,5140 \
  aadesaed.cat.app.App "$@"
