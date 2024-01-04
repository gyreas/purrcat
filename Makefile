comma       := ,
empty       :=
space       := $(empty) $(empty)

# build, jars, and tests dirs
SRC         := src
PURR        := $(SRC)/aadesaed/cat

PURRPKG     := aadesaed.cat.app
JARS        := slf4j.jar jcommander.jar testng.jar

TESTALL     := $(shell fd . -e java $(TESTPATH))
TESTRUNNER  := org.testng.TestNG
TESTPATH    := test
LOG         := test.log
CONST       := $(TESTPATH)/consistent/consistent.txt

# needed for compilation
INFO		:= "[INFO]"
NEWLINE     := $(shell echo "$(INFO) ------------------------------------------------------------------------")
DEPS        := $(addprefix $(PURR)/,app/App.java cmdline/Args.java input/ReadFile.java)
CLASSPATH   := $(subst $(space),:,$(addprefix lib/,$(JARS)))

$(PURR)/app/App.class: $(DEPS)
	@printf "%s Building the App.\n" $(INFO)
	@mvn compile
	@printf "%s Done.\n" $(INFO)
	@echo $(NEWLINE)

run: $(PURR)/app/App.class
	java -cp src/ aadesaed.cat.app.App

test: $(PURR)/app/App.class 
	@echo $(NEWLINE)
	@printf "%s Setting up the test environment..\n" $(INFO)

	@TESTPATH=$(TESTPATH) $(TESTPATH)/mk-outs.rb

	@printf "%s Done.\n" $(INFO)
	@printf "%s Running tests...\n" $(INFO)

	@echo $(NEWLINE)
	@mvn test

	@mv "$(CONST).tmp" "$(CONST)"

	@echo "$(INFO)"
	@echo "$(INFO) OK!"
	@echo $(NEWLINE)

clean:
	@printf "%s Cleaning up test enviroment..\n" $(INFO)
	@printf "%s rm -rf $(TESTPATH)/expected/*\n" $(INFO)

	@rm -rf $(TESTPATH)/expected/*

	@printf "%s mv '$(CONST).tmp' '$(CONST)'\n" $(INFO)

	@echo $(NEWLINE)
	@mvn clean
	@if [ -f "$(CONST).tmp" ]; then mv "$(CONST).tmp" "$(CONST)"; fi
