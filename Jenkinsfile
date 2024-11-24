pipeline {
    agent any
    environment {
            GITHUB_TOKEN = credentials('github_token') // Token configurado en Jenkins
    }
    triggers {
            // Disparar el trabajo al recibir notificaciones del webhook
            githubPush()
            githubPullRequest()
    }


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
        stage('Test and Coverage') {
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
        failure {
            script {
                def message = "Build failed for ${env.BRANCH_NAME}"
                githubNotify context: 'Jenkins CI', status: 'FAILURE', description: message, targetUrl: "${env.BUILD_URL}"
            }
        }
        success {
            script {
                githubNotify context: 'Jenkins CI', status: 'SUCCESS', description: "All checks passed!", targetUrl: "${env.BUILD_URL}"
            }
        }
    }
}