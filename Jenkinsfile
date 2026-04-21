pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Khrsany/week2_UI-Localization.git'
            }
        }

        stage('TEST STAGE') {
            steps {
                echo 'THIS IS NEW JENKINSFILE'
            }
        }

        stage('Check Environment') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
                bat 'docker --version'
            }
        }

        stage('Build & Test') {
            steps {
                dir('UI-Localization') {
                    bat 'mvn clean verify'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                    dir('UI-Localization') {
                        bat '''
                        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121:sonar ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.token=%SONAR_TOKEN% ^
                        -Dsonar.projectKey=fuelcalculator
                        '''
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t alabassa/fuelcalculator:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DOCKERHUB_CREDS', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push alabassa/fuelcalculator:latest
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline onnistui.'
        }
        failure {
            echo 'Pipeline epäonnistui. Tarkista loki.'
        }
    }
}