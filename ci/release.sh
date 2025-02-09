CI=1 make package
VERSION="$(git tag --sort -taggerdate | head -n1 | cut -c 2-)"

ls $(pwd)/target
echo "$version"

gh release create "v$VERSION"        \
   --generate-notes                  \
  $(pwd)/target/purrcat-$VERSION.jar \
  $(pwd)/target/purrcat
