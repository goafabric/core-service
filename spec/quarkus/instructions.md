# service setup
please convert the spring boot, java service in this workspac to quarkus with kotlin
- follow the build.gradle.kts inside `./spec/quarkus/build.gradle.kts` 
- follow the example service files inside the zipped file, which needs to be unzipped `./spec/quarkus/example.zip`
                                                                       
# requirements
- follow technical requirements inside `./spec/quarkus/technical-requirements.md`
- follow the requirements inside `./spec/quarkus/functional-requirements.md`
- for the quarkus target you can focus on jpa, existing implementation and dependecides for elasticsearch can be dropped for the quarkus target

# phased approach with PLAN.md
- please create a PLAN.md upfront to separate the approach into multiple phases
- this PLAN.md could be verified by the human in the loop upfront
- it should also be possible to resume the code generation after a session was closed and restarted
                                           
# verification
- when you are finished with code generation execute `gradlew clean build` to verify everything