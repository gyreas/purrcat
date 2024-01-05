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

tests:
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
	@#TODO: Implement this in Make syntax
	@if [ -f "$(CONST).tmp" ]; then mv "$(CONST).tmp" "$(CONST)"; fi
