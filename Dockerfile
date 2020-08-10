FROM openjdk:11

ENV mongo = mongo

COPY /target/*.jar /sarfo/
WORKDIR /sarfo/

CMD java -jar *.jar --spring.profiles.active=prod
