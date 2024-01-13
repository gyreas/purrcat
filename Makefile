comma       := ,
empty       :=
space       := $(empty) $(empty)

TESTPATH    := test
CONST       := $(TESTPATH)/consistent/consistent.txt

# infoing
INFO		:= "[INFO]"
NEWLINE     := $(shell echo " $(INFO)\
				 ------------------------------------------------------------------------")

build:
	@printf "%s Building the App...\n" $(INFO)
	@mvn compile
	@printf "%s Done.\n" $(INFO)
	@echo $(NEWLINE)

setup:
	@echo $(NEWLINE)
	@printf "%s Setting up the test environment..\n" $(INFO)

	@TESTPATH=$(TESTPATH) $(TESTPATH)/mk-outs.rb

	@printf "%s Done.\n" $(INFO)

tests: setup
	@printf "%s Running tests...\n" $(INFO)

	@echo $(NEWLINE)
	@TEST=1 mvn test

	@mv "$(CONST).tmp" "$(CONST)"

	@echo "$(INFO)"
	@echo "$(INFO) OK!"
	@echo $(NEWLINE)

package: setup
	@./format-all.sh
	@TEST=1 mvn package

clean:
	@printf "%s Cleaning up test enviroment..\n" $(INFO)
	@printf "%s rm -rf $(TESTPATH)/expected/*\n" $(INFO)

	@rm -rf $(TESTPATH)/expected/*

	@printf "%s mv '$(CONST).tmp' '$(CONST)'\n" $(INFO)

	@echo $(NEWLINE)
	@mvn clean
	@#TODO: Implement this in Make syntax
	@if [ -f "$(CONST).tmp" ]; then mv "$(CONST).tmp" "$(CONST)"; fi
