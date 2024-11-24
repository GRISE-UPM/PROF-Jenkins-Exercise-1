pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('github-token') // Token configurado en Jenkins
    }

    triggers {
        // Disparar el trabajo al recibir notificaciones del webhook
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
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

        stage('Code Coverage') {
            steps {
                sh './gradlew jacocoTestReport'
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
    }

    post {
        success {
            // Marcar el PR como exitoso
            updateGitHubStatus('success', 'Build successful')
        }
        failure {
            // Marcar el PR como fallido
            updateGitHubStatus('failure', 'Build failed')
        }
    }
}

