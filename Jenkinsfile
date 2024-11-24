pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Gradle Permissions') {
            steps {
                sh 'chmod +x gradlew'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Jacoco Report') {
            steps {
                sh './gradlew jacocoTestReport'
            }
        }
        stage('Verify Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
            jacoco execPattern: 'build/jacoco/test.exec', classPattern: 'build/classes/java/main', sourcePattern: 'src/main/java'
        }
    }
}