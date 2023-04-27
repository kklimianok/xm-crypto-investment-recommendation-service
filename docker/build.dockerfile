# -----------------------------------------------------------------------------
# Maven package and extract spring jar layers - Recommendation Service
#-----------------------------------------------------------------------------
FROM openjdk:11 AS builder
WORKDIR application
ARG DL_JAR_FILE=cirs-dataloader/target/*exec.jar
ARG API_JAR_FILE=cirs-rest/cirs-rest-api/target/*exec.jar
COPY ${DL_JAR_FILE} cirs-dl.jar
COPY ${API_JAR_FILE} cirs-api.jar
RUN java -Djarmode=layertools -jar cirs-dl.jar extract
RUN java -Djarmode=layertools -jar cirs-api.jar extract

# -----------------------------------------------------------------------------
# Create Final Docker Image
# -----------------------------------------------------------------------------
FROM openjdk:11
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]