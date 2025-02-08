TESTPATH    := test
CONST       := $(TESTPATH)/consistent/consistent.txt

PWD         := $(shell pwd)
SRC         := $(PWD)/src
APPDIR      := $(SRC)/java/aadesaed/cat/app
TARGETDIR   := $(PWD)/target
RESDIR      := $(APPDIR)/resources/META-INF
VERSION     := $(shell grep Version $(RESDIR)/application.properties | cut -d' ' -f2)
ARTIFACT    := purrcat-$(VERSION)
PACKAGE     := $(ARTIFACT).jar
NATIVEDIR   := $(SRC)/c
CFLAGS      := -O3 -g0 -Wall -Werror -Wpedantic -std=c89 -fPIC -shared -I$(NATIVEDIR)
LIB         := libpurrcatfstat.so
NAME        := aadesaed.cat.app.App
LIBSO       := $(TARGETDIR)/$(LIB)
SCRIPT      := $(TARGETDIR)/purrcat
# infoing
INFO        := "[INFO]"
NEWLINE     := "$(INFO) ------------------------------------------------------------------------"

# TODO: make this work with the new jar-orieneted architecture
build: $(LIBSO)
	@printf "%s Building the App...\n" $(INFO)
	@mvn compile
	@printf "%s Done.\n" $(INFO)
	@echo $(NEWLINE)

$(LIBSO): $(NATIVEDIR)/fstat.c $(NATIVEDIR)/fstat.h
	@echo $(NEWLINE)
	@echo "$(INFO) Building libfstat.so"

	@if [ ! -d $(TARGETDIR) ]; then mkdir $(TARGETDIR); fi

	@printf "$(INFO) "
	cc $(CFLAGS) -o $(LIBSO) $(NATIVEDIR)/fstat.c

	@printf "$(INFO) "
	strip $(LIBSO)

	@echo "$(INFO)"
	@echo "$(INFO) OK!"
	@echo $(NEWLINE)

setup: clean
	@echo $(NEWLINE)
	@printf "%s Setting up the test environment..\n" $(INFO)

	@NO_CONST=y TESTPATH=$(TESTPATH) $(TESTPATH)/mk-outs.rb

	@printf "%s Done.\n" $(INFO)

tests: setup build
	@printf "%s Running tests...\n" $(INFO)

	@echo $(NEWLINE)
	@TEST=1 mvn test

	if test "$(NO_CONST)" = "y"; then @mv "$(CONST).tmp" "$(CONST)"; fi

	@echo "$(INFO)"
	@echo "$(INFO) OK!"
	@echo $(NEWLINE)

package: setup $(LIBSO)
	@echo $(INFO)
	@echo "$(INFO) Making package: $(PACKAGE)"

	@echo "$(INFO) Formatting all source files"; ./format-all.sh

	@echo $(INFO)
	@echo $(INFO)
	@printf "$(INFO) "
	TEST=1 mvn -B package --file pom.xml

	@echo "$(INFO) ----------------------------< POST PACKAGE >----------------------------"

	@echo "$(INFO) Transporting artifacts for the package"
	rm "$(TARGETDIR)/$(ARTIFACT)" || true
	mkdir "$(TARGETDIR)/$(ARTIFACT)"
	cd "$(TARGETDIR)/$(ARTIFACT)";   \
	jar -xf "../$(PACKAGE)";         \
	cp $(LIBSO) ./META-INF/;         \
	jar -c -f ../$(PACKAGE) .;

	@printf "$(INFO) "
	printf "java -cp %s %s \$$@\n" $(TARGETDIR)/$(PACKAGE) $(NAME) > $(SCRIPT)

	@printf "$(INFO) "
	chmod +x $(SCRIPT)

	@printf "$(INFO) "; cat $(SCRIPT)

	@echo $(INFO);                  \
	echo "$(INFO) $(PACKAGE): OK!"; \
	echo $(NEWLINE)

clean:
	@printf "%s Cleaning up test enviroment..\n" $(INFO)
	@printf "%s rm -rf $(TESTPATH)/expected/*\n" $(INFO)

	@rm -rf $(TESTPATH)/expected/*

	@printf "%s mv '$(CONST).tmp' '$(CONST)'\n" $(INFO)

	@echo $(NEWLINE)
	@mvn clean
	@#TODO: Implement this in Make syntax
	@if [ -f "$(CONST).tmp" ]; then mv "$(CONST).tmp" "$(CONST)"; fi
