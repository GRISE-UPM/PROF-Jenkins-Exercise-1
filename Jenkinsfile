pipeline {
    agent any
    environment {
        GITHUB_TOKEN = credentials('ej4_repo_PAT') // Store your GitHub token securely in Jenkins credentials
        REPO_OWNER = 'OhhTuRnz'
        REPO_NAME = 'PROF-Jenkins-Exercise-1'
    }
    stages {
        stage('Initialize') {
            steps {
                script {
                    if (!env.ghprbPullId || !env.ghprbActualCommit) {
                        error "This pipeline must be triggered by a GitHub Pull Request event."
                    }
                    echo "Processing Pull Request #${env.ghprbPullId}"
                    echo "Commit SHA: ${env.ghprbActualCommit}"
                }
            }
        }

        stage('Set Status to Pending') {
            steps {
                script {
                    updateGitHubStatus('pending', 'Build in progress', 'ci/jenkins', null)
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('JaCoCo Test Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
    }

    post {
        success {
            script {
                updateGitHubStatus('success', 'Build and coverage check passed', 'ci/jenkins', env.BUILD_URL)
            }
        }

        failure {
            script {
                updateGitHubStatus('failure', 'Build or coverage check failed', 'ci/jenkins', env.BUILD_URL)
            }
        }
    }
}

def updateGitHubStatus(state, description, context, targetUrl) {
    def statusData = [
        state       : state,
        description : description,
        context     : context,
        target_url  : targetUrl ?: '' // Use an empty string if no targetUrl is provided
    ]

    sh """
    curl -X POST \
    -H "Authorization: token ${GITHUB_TOKEN}" \
    -H "Content-Type: application/json" \
    -d '${groovy.json.JsonOutput.toJson(statusData)}' \
    https://api.github.com/repos/${REPO_OWNER}/${REPO_NAME}/statuses/${env.ghprbActualCommit}
    """
}
