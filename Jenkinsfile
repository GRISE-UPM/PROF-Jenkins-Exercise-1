pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('github-token')
    }

    stages {
        stage('Checkout') {
            steps {
                // Clonar el repositorio
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build'
            }
        }
        stage('JaCoCo Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
    }
    
     post {
        success {
            githubNotify context: 'Build Status', status: 'SUCCESS', description: 'Build passed'
        }
        failure {
            githubNotify context: 'Build Status', status: 'FAILURE', description: 'Build failed'
        }
    }
}