mvn clean package
if [ -d ./'result' ]; then \
    rm -r ./'result'; \
fi ; \
java -jar target/lab3-1.0-SNAPSHOT-jar-with-dependencies.jar
start notepad++ ./'result'/'part-00000'