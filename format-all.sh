find test/ src/ -regex ".*\.java" -exec \
    java -jar "$HOME/Downloads/google-java-format-1.19.1-all-deps.jar" --replace {} \;
