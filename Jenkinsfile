pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('github-token')
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build'
            }
        }
        stage('Code Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
    }
    post {
        success {
            script {
                def prStatus = 'success'
                updateGitHubStatus(prStatus)
            }
        }
        
        failure {
            script {
                def prStatus = 'failure'
                updateGitHubStatus(prStatus)
            }
        }
    }
}
def updateGitHubStatus(String status) {
    
    withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
        def prUrl = "https://api.github.com/repos/pacots/PROF-Jenkins-Exercise-1/statuses/${env.GIT_COMMIT}"
        def data = """
        {
            "state": "${status}",
            "context": "Jenkins CI"
        }
        """
        sh "curl -X POST -H 'Authorization: token ${GITHUB_TOKEN}' -d '${data}' ${prUrl}"
    }
}