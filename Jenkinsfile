pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('Jenkins-GrupoZ')
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

        stage('JaCoCo Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
    }

    post {
        success {
            script {
                updateGitHubStatus('SUCCESS', 'Build and coverage passed')
            }
        }

        failure {
            script {
                updateGitHubStatus('FAILURE', 'Build or coverage failed')
            }
        }
    }
}

def updateGitHubStatus(String state, String description) {
    def context = "continuous-integration/jenkins"
    def commitSha = env.GIT_COMMIT

    withCredentials([string(credentialsId: 'Jenkins-GrupoZ', variable: 'GITHUB_TOKEN')]) {
        def apiUrl = "https://api.github.com/repos/carloslopllo/Jenkins-GrupoZ/statuses/${commitSha}"
        def data = """
        {
            "state": "${state}",
            "description": "${description}",
            "context": "${context}"
        }
        """
        sh "curl -X POST -H 'Authorization: token ${GITHUB_TOKEN}' -d '${data}' ${apiUrl}"
    }
}
