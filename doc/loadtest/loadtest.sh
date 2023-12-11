#!/bin/bash

baseurl=http://localhost:50800

function dorequest() {
  echo .
  curl "${baseurl}/practitioners/findByFamilyName?familyName="
  curl "${baseurl}/organizations/findByName?name="

  curl "${baseurl}/objects/search?search="

  curl "${baseurl}/insurances/findByDisplay?display=a"
}

while true
do
  dorequest
done

#docker run --pull always --name catalog-batch-service --rm -p50600:50600 -e 'management.otlp.tracing.endpoint=http://host.docker.internal:4318/v1/traces' goafabric/catalog-batch-service:1.2.0-SNAPSHOT