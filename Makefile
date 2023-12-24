comma       := ,
empty       :=
space       := $(empty) $(empty)

# build, jars, and tests dirs
SRC        := src
PURR       := $(SRC)/aadesaed/cat

PURRPKG    := aadesaed.cat.app
JARS       := slf4j.jar jcommander.jar testng.jar

TESTALL    := $(shell fd . -e java $(TESTPATH))
TESTPKG    := aadesaed.cat.tests
TESTRUNNER := org.testng.TestNG
TESTPATH   := $(PURR)/tests

# needed for compilation
DEPS        := $(PURR)/app/App.java $(PURR)/cmdline/Args.java $(PURR)/input/ReadFile.java
CLASSPATH   := $(subst $(space),:,$(addprefix lib/,$(JARS)))

$(PURR)/app/App.class: $(DEPS)
	@printf "Building the App.\n"
	javac -cp $(SRC):$(CLASSPATH) $(PURR)/app/App.java
	@printf "Done.\n"

run: $(PURR)/app/App.class
	java aadesaed.cat.app.App

# nah, going with TestNG, since it uses single .jar
test: $(PURR)/app/App.class 
	@printf "Setting up the test environment.\n"
	@TESTPATH=$(TESTPATH) $(TESTPATH)/mk-outs.rb
	@printf "Done.\n\n"
	@printf "Running tests..."
	@sleep 0.2
	@printf "\n-----------------------------------------------\n"
	@javac -cp $(SRC):$(CLASSPATH) $(TESTALL)
	@java -cp $(SRC):$(CLASSPATH) org.testng.TestNG -log 1 TestAll.xml 2> /dev/null
	@echo "OK!"

clean:
	@# Cleaning class files
	fd . -e class -I -x rm {}
	@# Removing test results
	rm -rf test-output/
	@# Clean up the environment
	rm -rf $(TESTPATH)/expected/*

