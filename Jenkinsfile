pipeline {
    agent any
    environment {
        GITHUB_TOKEN = credentials('github-token') // Token de GitHub
        ORIGINAL_REPO = 'GRISE-UPM/PROF-Jenkins-Exercise-1'
        FORK_REPO = 'chainiDV/PROF-Jenkins-Exercise-1' // Cambia 'your-username' por tu usuario
    }
    stages {
        stage('Detect Pull Requests') {
            steps {
                script {
                    echo "Checking pull requests in ${env.ORIGINAL_REPO}..."
                    def response = sh(
                        script: """
                        curl -s -H "Authorization: token ${GITHUB_TOKEN}" \\
                             -H "Accept: application/vnd.github+json" \\
                             https://api.github.com/repos/${ORIGINAL_REPO}/pulls
                        """,
                        returnStdout: true
                    )
                    def pulls = readJSON text: response

                    // Buscar pull requests del fork
                    for (pr in pulls) {
                        if (pr.head.repo.full_name == env.FORK_REPO) {
                            echo "Processing PR #${pr.number} from fork: ${pr.html_url}"
                            env.PR_NUMBER = pr.number
                            env.PR_COMMIT_SHA = pr.head.sha
                            break
                        }
                    }

                    if (!env.PR_NUMBER) {
                        error "No pull requests found from ${env.FORK_REPO}."
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "PR-${env.PR_NUMBER}"]],
                    userRemoteConfigs: [[
                        url: "https://github.com/${FORK_REPO}.git",
                        refspec: "+refs/pull/${env.PR_NUMBER}/merge:refs/remotes/origin/PR-${env.PR_NUMBER}"
                    ]]
                ])
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew test jacocoTestReport'
            }
        }

        stage('Validate Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
            }
        }

        stage('Update Pull Request Status') {
            steps {
                script {
                    echo "Updating pull request status in GitHub..."
                    def status = 'success'
                    def description = 'All checks passed'
                    sh """
                    curl -X POST -H "Authorization: token ${GITHUB_TOKEN}" \\
                        -H "Accept: application/vnd.github+json" \\
                        -d '{
                            "state": "${status}",
                            "description": "${description}",
                            "context": "continuous-integration/jenkins",
                            "target_url": "${env.BUILD_URL}"
                        }' https://api.github.com/repos/${ORIGINAL_REPO}/statuses/${env.PR_COMMIT_SHA}
                    """
                }
            }
        }
    }
    post {
        failure {
            script {
                def status = 'failure'
                def description = 'Build failed'
                sh """
                curl -X POST -H "Authorization: token ${GITHUB_TOKEN}" \\
                    -H "Accept: application/vnd.github+json" \\
                    -d '{
                        "state": "${status}",
                        "description": "${description}",
                        "context": "continuous-integration/jenkins",
                        "target_url": "${env.BUILD_URL}"
                    }' https://api.github.com/repos/${ORIGINAL_REPO}/statuses/${env.PR_COMMIT_SHA}
                """
            }
        }
    }
}
