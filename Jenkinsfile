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
// Función para actualizar el estado del pull request
def updateGitHubStatus(String state, String description) {
    def context = "continuous-integration/jenkins"
    def commitSha = env.GIT_COMMIT

    withCredentials([string(credentialsId: 'github-token', variable:'GITHUB_TOKEN')]) {
        def apiUrl = "https://api.github.com/EmilioAyuso/PROF-Jenkins-GrupoB/statuses/${commitSha}"
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