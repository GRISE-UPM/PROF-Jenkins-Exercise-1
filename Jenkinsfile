pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Starting Build') {
            steps {
                 sh 'chmod +x gradlew'
          }
        }
        stage('Building') {
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
            setGitHubPullRequestStatus  context: 'Builder', message: 'Build/tests failed.', state: 'FAILURE'
            script {
                def message = "Build failed for ${env.BRANCH_NAME}"
                githubNotify context: 'Jenkins CI', status: 'FAILURE', description: message, targetUrl: "${env.BUILD_URL}"
            }
        }
        success {
            setGitHubPullRequestStatus  context: 'Builder', message: 'All checks passed!', state: 'SUCCESS'
            script {
                githubNotify context: 'Jenkins CI', status: 'SUCCESS', description: "All checks passed!", targetUrl: "${env.BUILD_URL}"
            }
        }
    }
}
