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
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('JaCoCo Verification') {
            steps {
                sh './gradlew check'
            }
        }
    }
    post {
        always {
            script {
                if (currentBuild.result == 'SUCCESS') {
                    githubNotify context: 'continuous-integration/jenkins', status: 'success', description: 'Build successful', targetUrl: "${env.BUILD_URL}"
                } else {
                    githubNotify context: 'continuous-integration/jenkins', status: 'failure', description: 'Build failed', targetUrl: "${env.BUILD_URL}"
                }
            }
        }
    }
}
