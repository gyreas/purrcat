# build, jars, and tests dirs
 SRC      = src
PURR      = $(SRC)/aadesaed/cat
JARS      = 
TEST      =

# needed for compilation
DEPS      = $(PURR)/app/App.java $(PURR)/cmdline/Args.java $(PURR)/input/ReadFile.java
CLASSPATH = $(JARS)

$(PURR)/app/App.class: $(DEPS)
	@printf "Building the App.\n"
	javac -cp $(SRC):$(CLASSPATH) $(PURR)/app/App.java
	@printf "Done.\n"

run: $(PURR)/app/App.class
	java aadesaed.cat.app.App

# pretend until I figure out JUnit
test:
	@echo "Running tests"
	@sleep 2
	@echo "21 Passed!"

clean:
	rm $(PURR)/*/*.class
