comma       := ,
empty       :=
space       := $(empty) $(empty)

TESTPATH    := test
CONST       := $(TESTPATH)/consistent/consistent.txt

PWD			:= $(shell pwd)
SRC			:= $(PWD)/src
APPDIR		:= $(SRC)/java/aadesaed/cat/app
TARGETDIR	:= $(PWD)/target
RESDIR		:= $(APPDIR)/resources/META-INF
NATIVEDIR	:= $(SRC)/c
CFLAGS 		:= -Wall -Werror -Wpedantic -std=c89 -fPIC -shared -I$(NATIVEDIR)
LIB			:= libfstat.so
LIBSO		:= $(TARGETDIR)/$(LIB)
# infoing
INFO		:= "[INFO]"
NEWLINE     := $(shell echo " $(INFO)\
				 ------------------------------------------------------------------------")

build: $(LIBSO)
	@printf "%s Building the App...\n" $(INFO)
	@mvn compile
	@printf "%s Done.\n" $(INFO)
	@echo $(NEWLINE)

$(LIBSO): $(NATIVEDIR)/fstat.c $(NATIVEDIR)/fstat.h
	@if [ ! -d $(TARGETDIR) ]; then mkdir $(TARGETDIR); fi
	cc $(CFLAGS) -o $(LIBSO) $(NATIVEDIR)/fstat.c

setup:
	@echo $(NEWLINE)
	@printf "%s Setting up the test environment..\n" $(INFO)

	@TESTPATH=$(TESTPATH) $(TESTPATH)/mk-outs.rb

	@printf "%s Done.\n" $(INFO)

tests: build setup
	@printf "%s Running tests...\n" $(INFO)

	@echo $(NEWLINE)
	@TEST=1 mvn test

	@mv "$(CONST).tmp" "$(CONST)"

	@echo "$(INFO)"
	@echo "$(INFO) OK!"
	@echo $(NEWLINE)

purrcat: package
	@jar -xf $(TARGETDIR)/$(PACKAGE) /tmp/purrcat-$(shell date +'%m_%s')
	@cat <<< PURRCAT > purrcat.sh
		java -Djava.library.path=/
	PURRCAT

package: $(LIBSO) setup
	@./format-all.sh
	@cp $(LIBSO) $(RESDIR)/$(LIB)
	@TEST=1 mvn -B package --file pom.xml

clean:
	@printf "%s Cleaning up test enviroment..\n" $(INFO)
	@printf "%s rm -rf $(TESTPATH)/expected/*\n" $(INFO)

	@rm -rf $(TESTPATH)/expected/*

	@printf "%s mv '$(CONST).tmp' '$(CONST)'\n" $(INFO)

	@echo $(NEWLINE)
	@mvn clean
	@#TODO: Implement this in Make syntax
	@if [ -f "$(CONST).tmp" ]; then mv "$(CONST).tmp" "$(CONST)"; fi
