FROM maven:latest
WORKDIR /usr/src/service
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
CMD ["mvn", "spring-boot:run", "-DmyActiveProfile=docker"]