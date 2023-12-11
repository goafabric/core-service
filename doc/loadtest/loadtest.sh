#!/bin/bash

baseurl=http://localhost:50800

function dorequest() {
  echo .
  curl "${baseurl}/practitioners/findByFamilyName?familyName="
  curl "${baseurl}/organizations/findByName?name="

  curl "${baseurl}/objects/search?search="

  curl "${baseurl}/insurances/findByDisplay?display=a"

  practitioner_info=$(curl -s "${baseurl}/practitioners/findByFamilyName?familyName=Hibbert") && curl -X POST -H "Content-Type: application/json" -d '{
    "id": "'$(echo "$practitioner_info" | jq -r '.[0].id')'",
    "version": "'$(echo "$practitioner_info" | jq -r '.[0].version')'",
    "givenName": "Dr. Julius",
    "familyName": "Hibbert",
    "gender": "male",
    "birthDate": "2020-01-08",
    "lanr": "4711"
  }' "${baseurl}/practitioners/save"

}

while true
do
  dorequest
done

#docker run --pull always --name catalog-batch-service --rm -p50600:50600 -e 'management.otlp.tracing.endpoint=http://host.docker.internal:4318/v1/traces' goafabric/catalog-batch-service:1.2.0-SNAPSHOT


practitioner_info=$(curl -s "${baseurl}/practitioners/findByFamilyName?familyName=Hibbert")

practitioner_info=$(curl -s "${baseurl}/practitioners/findByFamilyName?familyName=Hibbert") && curl -X POST -H "Content-Type: application/json" -d '{
  "id": "'$(echo "$practitioner_info" | jq -r '.[0].id')'",
  "version": "'$(echo "$practitioner_info" | jq -r '.[0].version')'",
  "givenName": "Dr. Julius",
  "familyName": "Hibbert",
  "gender": "male",
  "birthDate": "2020-01-08",
  "lanr": "4711"
}' "${baseurl}/practitioners/save"

curl -s "${baseurl}/practitioners/findByFamilyName?familyName=Hibbert"