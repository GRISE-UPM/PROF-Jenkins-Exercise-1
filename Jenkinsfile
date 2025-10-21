pipeline {
    agent any
    stages {
        stage('Build and test') {
            steps {
                echo 'Running mvn verify (will run JaCoCo and checks)'
                sh 'mvn -B -DskipTests=false verify'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'target/site/jacoco/**', allowEmptyArchive: true
        }
    }
}
