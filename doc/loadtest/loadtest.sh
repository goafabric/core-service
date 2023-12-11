#!/bin/bash

baseurl=http://localhost:50800
catalogurl=http://localhost:50600

function dorequest() {
  echo .
  curl "${baseurl}/practitioners/findByFamilyName?familyName="
  curl "${baseurl}/organizations/findByName?name="

  curl "${baseurl}/objects/search?search="

  curl "${catalogurl}/chargeitems/findByDisplay?display=u"
  curl "${catalogurl}/insurances/findByDisplay?display=a"
}

while true
do
  dorequest
done
