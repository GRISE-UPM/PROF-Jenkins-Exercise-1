pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('github-token')
        GRADLE_OPTS = "-Dorg.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL=86400"
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
                sh './gradlew clean build --info'
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
            echo 'Build successful'
        }
        failure {
            echo 'Build failed'
        }
    }
}