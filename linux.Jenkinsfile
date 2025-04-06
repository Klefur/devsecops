pipeline {
    agent any
    tools {
        gradle "gradle"
    }
    stages {
        stage('Build JAR File') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Klefur/devsecops']])
                dir("main"){
                    sh "gradle clean build"
                }
            }
        }
        stage("Test") {
            steps {
                dir("main"){
                    sh "gradle test"
                }
            }
        }        
        stage("Build and Push Docker Image") {
            steps {
                dir("main"){
                    script {
                        withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                        }
                        sh "docker build -t klefurusach/tingeso-pep1 ."
                        sh "docker push klefurusach/tingeso-pep1"
                        sh "docker logout"
                    }
                }
            }
        }
    }
}    