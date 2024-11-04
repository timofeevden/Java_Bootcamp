# Cd in project folder and make sure to have target directory
	rm -rf target && mkdir target

# Compile
	javac -d ./target ./src/java/edu/school21/printer/*/*.java

# Copy resources
	cp -r ./src/resources ./target/

# Compress to jar
	jar cvfm ./target/images-to-chars-printer.jar ./src/manifest.txt -C ./target/ .

# Run application
	java -jar target/images-to-chars-printer.jar . 0
