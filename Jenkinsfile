pipeline {
    agent any
//    tools {
//        maven 'Maven'
//    }
    tools {
        dockerTool 'Docker'
    }

    environment {
        DATE = sh(script: "date +%Y-%m-%d_%H-%M-%S", returnStdout: true).trim()
        REPORT_DIR = "reports/${DATE}"
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn clean install -DskipTests"
                sh "docker build -t rest-assured ."
            }
        }
        stage('Test') {
            steps {
                script {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        withCredentials([usernamePassword(
                                credentialsId: 'REST_BOOKER_CREDS',
                                usernameVariable:'RESTBOOKER_USERNAME',
                                passwordVariable:'RESTBOOKER_PASSWORD'
                        )]) {
     //                       sh "mvn test -P${profile}"
                            sh "docker run -v \${pwd}/allure-results:app/allure-results" +
                                    " -e RESTBOOKER_USERNAME -e RESTBOOKER_PASSWORD -e MAVEN_PROFILE=${profile}" +
                                    "rest_assured"
                        }
                    }
                }
            }
        }
        stage('Generate report') {
            steps {
                script{
                    allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
                }
            }
        }
        stage('Archive report') {
            steps {
                archiveArtifacts artifacts: "allure-report/**", fingerprint: true
            }
        }
    }
}