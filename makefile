

all:
	javac -cp '.:./org.json.jar' *.java

xlint:
	javac -cp '.:./org.json.jar' *.java -Xlint

run:
	java -cp '.:./org.json.jar' HueLights
