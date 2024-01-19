## A cat
A Java implementation of Unix cat. No meows, please.
It's a toy program to improve my familiarity with the Java ecosystem.

### How to build
> **NOTE** </br>
> You'll need to have installed Java SE 17, `make` and Apache `maven`.

1. Clone the repo: `git clone https://github.com/gyreas/purrcat.git`
2. Enter the dir: `cd purrcat`
3. Build with: `make build`
4. Run the tests with: `make tests` (note the 's' in 'tests')
5. Test with: `./app.sh <options & files here>`
6. To build a standalone JAR, do: `make clean package`

### TODO
- [x] implement tiny testing library / find a tiny library jar
- [x] add the main branch after test suites is up
- [x] implement file i/o etc
- [x] implement feature's of Unix cat, so that this cat can purr.
- [x] Majestic error/exception handling
- [ ] Detect if output file is among input files to avoid blowing the file size.
- [ ] reading from stdin and
  - [x] stdin
  - [ ] sockets
- [x] bundle as jar, to infinity
- [x] upgrade to maven
- [ ] upgrade to gradle
- [ ] no more than 4x slower than Unix cat--this purrcat can run too.
