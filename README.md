# sonarqube
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=org.goafabric%3Acore-service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=org.goafabric%3Acore-service)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=org.goafabric%3Acore-service&metric=coverage)](https://sonarcloud.io/summary/new_code?id=org.goafabric%3Acore-service)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=org.goafabric%3Acore-service&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=org.goafabric%3Acore-service)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.goafabric%3Acore-service&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=org.goafabric%3Acore-service)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=org.goafabric%3Acore-service&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=org.goafabric%3Acore-service)

# docker compose
go to /src/deploy/docker and do "./stack up" or "./stack up -native"

# run jvm multi image
docker run --pull always --name core-service --rm -p50800:50800 goafabric/core-service:$(grep '^version=' gradle.properties | cut -d'=' -f2)

# run native image
docker run --pull always --name core-service-native --rm -p50800:50800 goafabric/core-service-native:$(grep '^version=' gradle.properties | cut -d'=' -f2) -Xmx64m
