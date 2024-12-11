pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Run Tests and Coverage') {
            steps {
                sh './gradlew test jacocoTestReport'
            }
        }
    }
    post {
        always {
            junit 'build/test-results/test/*.xml'
            jacoco execPattern: 'build/jacoco/test.exec', classPattern: 'build/classes/java/main', sourcePattern: 'src/main/java', exclusionPattern: ''
        }
    }
}
