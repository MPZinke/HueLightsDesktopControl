

all:
	javac -cp '.:./org.json.jar' *.java

jar:
	jar cfm HueLights.jar HueLightsMainfest.txt *.class org.json.jar

run:
	java -cp '.:./org.json.jar' HueLights
