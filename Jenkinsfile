pipeline {
  agent any 
  options {
    buildDiscarder(logRotator(numToKeepStr: '15', artifactNumToKeepStr: '15'))
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
    stage("SonarQube") {
      steps {
        def name = "${BRANCH_NAME}".replaceAll(/\\/, "_");
        sh "echo name is ${name}"
        withSonarQubeEnv('SonarQube Scanner') {
          sh './gradlew -Pjob=ppt-demo_${name} ui:sonarqube'
        }
      }
    }  
    stage("Quality Gate") {
      options {
        timeout(time: 1, unit: 'HOURS') 
      }
      steps {
        timeout(time: 1, unit: 'HOURS') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
    stage("DEV Deployment") {
      when {
        branch 'develop'
      }
      steps {
        sh 'echo deploying dev'
      }
    }
  }
      // stage ('API test') {
    // 	sh './gradlew api:clean api:build'
    // }
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
