# Cd in project folder and make sure to have target directory
	rm -rf target && mkdir target


# Unpack libs
	cd target && jar -xf ../lib/jcommander-1.83.jar && jar -xf ../lib/JColor-5.5.1.jar && rm -rf META-INF && cd ..


# Compile
	javac -classpath ./lib/jcommander-1.83.jar:./lib/JColor-5.5.1.jar -d ./target ./src/java/edu/school21/printer/*/*.java


# Copy resources
	cp -r ./src/resources ./target/


# Run without JAR
	java -classpath ./target edu.school21.printer.app.Program --white=RED --black=GREEN


# Compress to jar
	jar cvfm ./target/images-to-chars-printer.jar ./src/manifest.txt -C ./target/ .


# Run application
	java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
