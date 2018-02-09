# End-to-End Testing

## Requirements
- NodeJs
- JDK 8
- Docker (optional)

## Setup
- Run `npm install` from the **ui/** directory to install Node dependencies 
or you can use the gradle task `npmInstall` which will also install NodeJS if it's not already installed.

## Running the API
There are two Spring profiles that can be used when running the API, **default** and **e2e**.

#### default
The **default** profile requires Postgres to be running locally on port 5432. There is a docker-compose.yml
in the root directory to spin up a Postgres instance with the desired settings. To start the default
profile run the gradle task `bootRun`.

#### e2e
The **e2e** profile uses an embedded H2 database and returns canned responses when reaching out to external
services, in this case the Github REST API. To start the e2e profile run the gradle task `bootRun` but make
sure you set a JVM option or an environment variable: `spring.profiles.active=e2e`. The e2e profile starts
with some canned data in the database as well and two users defined in the e2e-data.sql file. Each user defined
in the file has their username set as their password.

## Running the UI
To start the UI run the `npm start` command from the **ui/** directory or run the gradle task `npmStart`.

## Running the end-to-end tests locally
To run the e2e tests locally, ensure the api is running locally using the e2e profile and run the `npm run e2e`
command from the **ui/** directory and the e2e tests will start. Artifacts from the test are located in the
**ui/build/** directory including images from the e2e tests.

## Running the build on a CI server
To run the build on a CI server that has docker capabilities run the following tasks:

`./gradlew clean build` This command will test and build the **api** module

```
docker run --rm \
	-v $(pwd):/usr/src/app \
    -e spring.profiles.active=e2e \
    local/node-chrome \
    sh -c "java -jar api/build/libs/api*.jar & cd ui && npm install && npm run test:ci && /usr/src/app/docker/wait-for-it.sh localhost:8080 -s -t 120 -- npm run e2e:ci"
```
This command will run the UI unit and e2e tests in a docker container. The image used is defined in the **docker/** folder. The image can also be built by running the command: `docker-compose -f docker-compose-build.yml build`. This will result
in an image named **local/node-chrome** being created.

The build produces a number of artifacts that can be gathered for test coverage and test completion:
- Junit test results: **\*\*/build/test-results/\*\*/*.xml**
- JaCoCo code coverage: exec files: **\*\*/\*\*.exec**, classes: **\*\*/classes**, source: **\*\*/src/main/java**
- UI test coverage report directory: **ui/build/test-results/coverage**
- API test coverage report directory: **api/build/reports/coverage**
- End-to-End test screenshots: **ui/build/test-results/e2e/images/\***
