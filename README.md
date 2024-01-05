## A cat
A Java implementation of Unix cat. No meows, please.
It's a toy program to improve my familiarity with the Java ecosystem.

### How to build
> **NOTE**: you'll need to have installed the `make` program

1. Clone the repo: `git clone https://github.com/gyreas/purrcat.git`
2. Enter the dir: `cd purrcat`
3. Build with: `make`
4. Run the tests with: `make tests` (note the 's' in 'tests')
5. Test with: `./app.sh <options & files here>`

### TODO
- [x] implement tiny testing library / find a tiny library jar
- [x] add the main branch after test suites is up
- [x] implement file i/o etc
- [x] implement feature's of Unix cat, so that this cat can purr.
- [ ] use functional programming techniques, ie, implement each feature as a 
     function that can simultaneously be applied to a file line-wise
- [ ] Majestic error/exception handling
- [ ] reading from stdin and pipes (difference?)
- [ ] versioning via Java git api
- [ ] bundle as jar, to infinity
- [x] upgrade to maven
- [ ] upgrade to gradle
