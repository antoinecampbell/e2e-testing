pipeline {
  agent any 
  options {
    buildDiscarder(logRotator(numToKeepStr: '30', artifactNumToKeepStr: '30'))
  }
  stages {
    stage('SCM') {
      steps {
        checkout scm
      }
    }
    stage('UI test') {
      steps {
    	  sh './gradlew ui:unitTestCI'
      }
    }  
  }
  stage("SonarQube") {
    steps {
      withSonarQubeEnv('SonarQube Scanner') {
        sh './gradlew ui:sonarqube'
      }
    }
  }
      // stage ('API test') {
    // 	sh './gradlew api:clean api:build'
    // }
  stage("Quality Gate"){
    steps {
      timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
        def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
        if (qg.status != 'OK') {
          error "Pipeline aborted due to quality gate failure: ${qg.status}"
        }
      }
    }
  }
}

// node {
//     stage('Checkout') {
//         git 'https://github.com/antoinecampbell/e2e-testing.git'
//     }
//     // stage('API Test') {
//     //     sh './gradlew api:clean api:build'
//     // }
//     // stage('E2E-Tests') {
//     //     sh '''
//     //         docker run --rm -v $(pwd):/usr/src/app -e spring.profiles.active=e2e \
//     //         local/node-chrome \
//     //         sh -c "java -jar api/build/libs/api*.jar & cd ui && /usr/src/app/docker/wait-for-it.sh localhost:8080 -s -t 120 -- npm run e2e:ci"
//     //     '''
//     // }
//     // stage('Test Results') {
//     //     junit '**/build/test-results/**/*.xml'
//     //     jacoco()
//     //     publishHTML([
//     //         allowMissing: false, 
//     //         alwaysLinkToLastBuild: false, 
//     //         keepAll: false, 
//     //         reportDir: 'api/build/reports/coverage', 
//     //         reportFiles: 'index.html', 
//     //         reportName: 'API Coverage', 
//     //         reportTitles: ''])
//     //     archiveArtifacts 'ui/build/test-results/e2e/images/*'
//     // }
// }
