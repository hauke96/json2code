# json2code
A simple converter to turn json into classes
# Usage
Just create a scheme file (s. `example.json` and `example.md`) and then pack the source into an executable jar:

`ant clean build && ant -buildfile build_jar.xml`

Then just start the jar file (as an example):

`java -jar jars/json2code.jar example.json java`

Of course you can also open the project in `eclipse` and export the jar (or start the application there).
