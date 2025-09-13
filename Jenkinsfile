pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    environment {
        DATE = sh(script: "date +%Y-%m-%d_%H-%M-%S", returnStdout: true).trim()
        REPORT_DIR = "reports/${DATE}"
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn clean install -DskipTests"
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
                            sh "mvn test -Pregression"
                        }
                    }
                }
            }
        }
        stage('Generate report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
            }
        }
        stage('Archive report') {
            steps {
                archiveArtifacts artifacts: "${REPORT_DIR}/**", fingerprint: true
            }
        }
    }
}