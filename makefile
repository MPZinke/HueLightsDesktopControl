

all:
	javac -cp '.:./org.json.jar' *.java

run:
	java -cp '.:./org.json.jar' HueLights
