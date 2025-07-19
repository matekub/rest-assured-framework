pipeline{
    agent any
    tools {
        maven 'Maven'
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn clean install -DskipTests"
            }
        }
        stage('Test') {
            steps {
                sh "mvn test -Pregression"
            }
        }
        stage('Reports') {
            steps {
                allure includeProperties: false, jdk:'', results:[[path: 'allure-results']]
            }
        }
    }
}