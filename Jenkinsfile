pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    environment {
        // Määritetään tietokantatiedot ympäristömuuttujina tehtävänannon mukaan
        DB_URL = "jdbc:mariadb://localhost:3306/fuel_calculator_localization"
        DB_USER = "root"
        DB_PASS = "Abbuusi"
    }

    stages {
        stage('Fetch Code') {
            steps {
                // Haetaan koodi repositoriosta
                checkout scm
            }
        }

        stage('Build and Compile') {
            steps {
                // Tehdään puhdistus ja käännös
                bat 'mvn clean compile'
            }
        }

        stage('Create Package') {
            steps {
                // Luodaan lopullinen jar-tiedosto ja hypätään testien yli jos niitä ei ole
                bat 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Build valmistui onnistuneesti!'
        }
        failure {
            echo 'Build epäonnistui, tarkista lokit.'
        }
    }
}