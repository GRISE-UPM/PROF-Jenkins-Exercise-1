pipeline {
    agent any

    environment {
        GITHUB_TOKEN = credentials('github_pat') // Aquí se usa el ID de las credenciales configuradas en Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                sh './gradlew clean build test jacocoTestReport'  // Ajusta este comando a tu entorno de construcción
            }
        }

        stage('Verify Coverage') {
            steps {
                script {
                    def coverage = sh(script: "grep -oP 'coverage rate: \\K[0-9.]+' build/reports/jacoco/test/html/index.html", returnStdout: true).trim()
                    if (coverage.toFloat() < 80.0) { // Ajusta el nivel mínimo de cobertura según tu requerimiento
                        error "Cobertura insuficiente: ${coverage}%"
                    }
                }
            }
        }

        stage('Update GitHub Status') {
            steps {
                script {
                    def status = currentBuild.result == 'SUCCESS' ? 'success' : 'failure'
                    sh """
                        curl -X POST -H "Authorization: token ${GITHUB_TOKEN}" \
                        -H "Content-Type: application/json" \
                        -d '{"state": "${status}", "target_url": "${env.BUILD_URL}", "description": "Build status", "context": "continuous-integration/jenkins"}' \
                        https://api.github.com/repos/tu-usuario/tu-repo/statuses/${env.GIT_COMMIT}
                    """
                }
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
            archiveArtifacts artifacts: 'build/reports/**', allowEmptyArchive: true
        }
    }
}
