pipeline {
    agent any
    tools {
        gradle "gradle"
    }
    environment {
        ZAP_CONTAINER_NAME = 'zap'  // Nombre del contenedor ZAP en ejecución
        ZAP_PORT = '80'          // Puerto en el que ZAP está escuchando
        SONARQUBE_SERVER = 'SonarQube'
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
                        withCredentials([usernamePassword(credentialsId: 'docker-credentials2', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            bat 'docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%'
                        }
                        bat "docker build -t klefurusach/tingeso-pep1 ."
                        bat "docker push klefurusach/tingeso-pep1"
                        bat "docker logout"
                    }
                }
            }
        }
        
        stage('Run OWASP Dependency-Check (SCA)') {
            steps {
                dir('main'){
                    script {
                        // Ejecutar OWASP Dependency-Check
                        bat 'gradle dependencyCheckAnalyze'  // Análisis de dependencias
                    }
                }
            }
        }
        
        stage("SAST Test - SonarQube") {
           steps {
                dir("main") {
                withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                    withSonarQubeEnv(SONARQUBE_SERVER) {
                        bat "gradle sonar -Dsonar.token=${env.SONAR_TOKEN}"
                        }
                    }
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
        stage('Run ZAP Baseline Scan') {
            steps {
                script {
                    try {
                        // Desactivar el comportamiento de fallo automático en errores
                        bat '''
                            set +e
                            docker exec -u root zap zap-baseline.py -t http://nginx-proxy -r zap_report.html -J zap_out.json
                            ZAP_EXIT=$?
                            set -e

                            if [ "$ZAP_EXIT" -eq 2 ]; then
                                echo "ZAP scan completed."
                                ZAP_EXIT=0
                            fi

                            exit $ZAP_EXIT
                        '''
                    } catch (Exception e) {
                        // Si ocurre algún error, marcar el build como exitoso pero con un aviso
                        currentBuild.result = 'SUCCESS'  // No fallar el build si hay un error
                        echo 'ZAP scan completed with warnings or report generation failure.'
                    }
                }
            }
        }



    }
}
