# End-to-End Testing

## Requirements
- NodeJs
- JDK 8
- Docker (optional)

## Setup
- Run `npm install` from the **ui/** directory to install Node depenencies 
or you can use the gradle task `npmInstall` which will alaso install NodeJS if it's not already installed.

## Running the API
There are two Spring profiles that can be used when running the API, **default** and **e2e**.

#### default
The **default** profile requires Postgres to be running locally on port 5432. There is a docker-compose.yml
in the root directory to sping up a Postgres instance with the desired settings. To start the default
profile run the gradle task `bootRun`.

#### e2e
The **e2e** profile uses an embedded H2 database and returns canned responses when reaching out to external
services, in this case the Github REST API. To start the e2e profile run the gradle task `bootRun` but make
sure you set a JVM option or an environment variable: `spring.profiles.active=e2e`. The e2e profile starts
with some canned data in the datbase as well and two users defined in the e2e-data.sql file. Each user defined
in the file has their username set as their password.

## Running the UI
To start the UI run the `npm start` command from the **ui/** directory or run the gradle task `npmStart`.

## Running the end-to-end tests
To run the e2e tests locally, ensure the api is running locally using the e2e profile and run the `npm run e2e`
command from the **ui/** directory and the e2e tests will start. Artifacts from the test are located in the
**ui/build/** directory including images from the e2e tests.
