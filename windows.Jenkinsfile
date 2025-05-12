pipeline {
    agent any
    tools {
        gradle "gradle"
    }
    stages {
        stage('Build JAR File') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Klefur/devsecops']])
                dir("main") {
                    bat "gradle clean build"
                }
            }
        }
        stage("Test") {
            steps {
                dir("main") {
                    bat "gradle test"
                }
            }
        }        
        stage("Build and Push Docker Image") {
            steps {
                dir("main") {
                    script {
                        withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            bat 'docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%'
                        }
                        bat "docker build -t klefurusach/tingeso-pep1 ."
                        bat "docker push klefurusach/tingeso-pep1"
                        bat "docker logout"
                    }
                }
            }
        }
        stage("SAST Test - SonarQube"){
            steps{
                dir("main"){
                    bat "gradle sonar"
                }
            }
        }

        stage("Deploy") {
            steps {
                dir("main") {
                    script {
                        bat "docker compose up -d --build"
                    }
                }
            }
        }
    }
}    