#!/usr/bin/env bash

ROOT="tests/inputs"
OUTDIR="tests/expected"

EMPTY="$ROOT/empty.txt"
FOX="$ROOT/fox.txt"
BUSTLE="$ROOT/the-bustle.txt"
SPIDERS="$ROOT/spiders.txt"
ALL="$BUSTLE $EMPTY $FOX $SPIDERS"

for FILE in $ALL; do
  BASENAME=$(basename -s .txt "$FILE")
  # using the files directly
  cat    $FILE > ${OUTDIR}/${BASENAME}.out
  cat -b $FILE > ${OUTDIR}/${BASENAME}_b.out
  cat -n $FILE > ${OUTDIR}/${BASENAME}_n.out

  # passing the content of the file to cat's stdin
  cat    < $FILE > ${OUTDIR}/${BASENAME}_stdin.out
  cat -b < $FILE > ${OUTDIR}/${BASENAME}_b_stdin.out
  cat -n < $FILE > ${OUTDIR}/${BASENAME}_n_stdin.out
done

cat         $ALL > $OUTDIR/all.out
cat -b      $ALL > $OUTDIR/all_b.out
cat -n      $ALL > $OUTDIR/all_n.out

