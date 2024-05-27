# First stage: build the WAR file using Maven
FROM maven:3.8.6-jdk-11 as builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the WAR file
COPY src ./src
RUN mvn clean package

# Second stage: deploy the WAR file to WildFly
FROM jboss/wildfly:latest

# Set the location of the WildFly installation
ENV WILDFLY_HOME /opt/jboss/wildfly

# Copy the WAR file from the builder stage
COPY --from=builder /app/target/*.war $WILDFLY_HOME/standalone/deployments/

# Expose the default WildFly port
EXPOSE 8080

# Start WildFly in standalone mode with the default configuration
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
