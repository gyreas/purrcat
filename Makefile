build:
	@[ -d classes ] || mkdir -p classes
	@printf "Building classes.\n"
	javac -cp classes/ -d classes {app,cmdline,input,text}/*.java
	@printf "Done.\n"

run:
	@printf "Running app.\n"
	java -cp classes App

