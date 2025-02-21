# sonarqube
[dashboard](https://v2202402203466256255.megasrv.de/sonar/dashboard?id=org.goafabric%3Acore-service)

# docker compose
go to /src/deploy/docker and do "./stack up" or "./stack up -native"

# run jvm multi image
docker run --pull always --name core-service --rm -p50800:50800 goafabric/core-service:$(grep '^version=' gradle.properties | cut -d'=' -f2)

# run native image
docker run --pull always --name core-service-native --rm -p50800:50800 goafabric/core-service-native:$(grep '^version=' gradle.properties | cut -d'=' -f2) -Xmx64m

# run native image arm
docker run --pull always --name core-service-native --rm -p50800:50800 goafabric/core-service-native-arm64v8:$(grep '^version=' gradle.properties | cut -d'=' -f2) -Xmx92m