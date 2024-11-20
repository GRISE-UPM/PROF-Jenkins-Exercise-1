pipeline {
    agent any

    environment {
        GRADLE_HOME = '/usr/local/gradle'
        PATH = "${GRADLE_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: '*/main']],
                          userRemoteConfigs: [[url: 'https://github.com/EmilioAyuso/PROF-Jenkins-GRUPO-B.git',
                                               credentialsId: '7da4655a-cfd6-4983-ad62-08f7fbfe6c34']]])
                sh 'java -version'
                sh 'ls -l gradle/wrapper/'
            }
        }
        
        stage('Build') {
            steps {
                // Asegurar permisos de ejecución
                sh 'chmod +x gradlew'
                sh './gradlew build --stacktrace'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Coverage') {
            steps {
                sh './gradlew jacocoTestReport'
            }
        }

        stage('Verify Coverage') {
            steps {
                sh './gradlew check'
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/**/*.xml'
            jacoco execPattern: '**/build/jacoco/*.exec', classPattern: '**/build/classes/java/main', sourcePattern: 'src/main/java'
        }
        failure {
            echo 'Build failed!'
        }
        success {
            echo 'Build succeeded!'
        }
    }
}
