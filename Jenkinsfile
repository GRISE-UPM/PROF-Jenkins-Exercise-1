pipeline {
    agent any
    environment {
        // Variables necesarias para Gradle
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Set Pending Status') {
            steps {
                setGitHubPullRequestStatus context: 'CI-Gradle-PipeLine', message: 'Pipeline is running...', state: 'PENDING'
            }
        }

       stage('Prepare Build') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Coverage') {
            steps {
                sh './gradlew jacocoTestReport'
            }
        }

        stage('Verify Coverage') {
            steps {
                sh './gradlew check'
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/**/*.xml'
            jacoco execPattern: '**/build/jacoco/*.exec', classPattern: '**/build/classes/java/main', sourcePattern: 'src/main/java'
        }
        success {
            setGitHubPullRequestStatus  context: 'CI-Gradle-PipeLine', message: 'All checks passed!', state: 'SUCCESS'
        }
        failure {
            setGitHubPullRequestStatus  context: 'CI-Gradle-PipeLine', message: 'Build or tests failed.', state: 'FAILURE' 
        }
    }
}

