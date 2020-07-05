

all:
	javac -cp 'build:src/org.json.jar' src/*.java -d build

jar:
	jar -cvmf ./src/manifest.txt HueLights.jar ./build/*.class ./src/org.json.jar

run:
	java -cp 'build:src/org.json.jar' -classpath build HueLights
