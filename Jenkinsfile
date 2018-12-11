pipeline {
    stages {
        stage('scm') {
            git 'https://github.com/JayFialkowski/e2e-testing.git'
        }
        stage('ui test') {
            sh './gradlew ui:unitTestCI'
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
//     // stage('UI Test') {
//     //     sh './gradlew ui:unitTestCI'
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
//     //     publishHTML([
//     //         allowMissing: false, 
//     //         alwaysLinkToLastBuild: false, 
//     //         keepAll: false, 
//     //         reportDir: 'ui/build/test-results/coverage', 
//     //         reportFiles: 'index.html', 
//     //         reportName: 'UI Coverage', 
//     //         reportTitles: ''])
//     //     archiveArtifacts 'ui/build/test-results/e2e/images/*'
//     // }
// }
