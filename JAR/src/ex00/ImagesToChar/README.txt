# Cd in project folder and make sure to have target directory
	rm -rf target && mkdir target

# Compile
	javac -d ./target ./src/java/edu/school21/printer/*/*.java

# Run application
	java -classpath ./target edu.school21.printer.app.Program . 0 ../it.bmp