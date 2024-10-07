pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/aynuod/JPA-Jenkins.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat 'mvnw sonar:sonar -Dsonar.projectKey=spring-test -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_f70d1c126c922be5d6465ea03e2d1440d5ae8303'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        echo "Quality Gate status: ${qg.status}"
                        if (qg.status != 'OK') {
                            currentBuild.result = 'FAILURE'
                            echo "Sending failure email with Swaks"
                            bat """
                                swaks --to dounyagourja2@gmail.com ^
                                      --from "chakra.hs.business@gmail.com" ^
                                      --server "smtp.gmail.com" ^
                                      --port "587" ^
                                      --auth PLAIN ^
                                      --auth-user "chakra.hs.business@gmail.com" ^
                                      --auth-password "pnuw lgzu ofkv oyoq" ^
                                      --helo "localhost" ^
                                      --tls ^
                                      --data "Subject: SonarQube Quality Gate failed\\n\\nThe SonarQube analysis has failed the Quality Gate for ${env.JOB_NAME}. See details at ${env.BUILD_URL}."
                            """
                        } else {
                            echo "Sending success email with Swaks"
                            bat """
                                swaks --to dounyagourja2@gmail.com ^
                                      --from "chakra.hs.business@gmail.com" ^
                                      --server "smtp.gmail.com" ^
                                      --port "587" ^
                                      --auth PLAIN ^
                                      --auth-user "chakra.hs.business@gmail.com" ^
                                      --auth-password "pnuw lgzu ofkv oyoq" ^
                                      --helo "localhost" ^
                                      --tls ^
                                      --data "Subject: SonarQube Quality Gate passed\\n\\nThe SonarQube analysis has passed the Quality Gate for ${env.JOB_NAME}. See details at ${env.BUILD_URL}."
                            """
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline completed. Swaks email should have been sent."
        }
        failure {
            echo "Pipeline failed. Check Jenkins logs for more details."
        }
    }
}
