pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token')
        EMAIL_PASSWORD = credentials('email-password')
    }

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
                    bat "mvnw sonar:sonar -Dsonar.projectKey=spring-test -Dsonar.host.url=http://localhost:9000 -Dsonar.login=%SONAR_TOKEN%"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        echo "Quality Gate status: ${qg.status}"

                        def emailSubject = qg.status == 'OK' ? "SonarQube Quality Gate passed" : "SonarQube Quality Gate failed"
                        def emailBody = """The SonarQube analysis has ${qg.status == 'OK' ? 'passed' : 'failed'} the Quality Gate for ${env.JOB_NAME}.

Quality Gate Details:
Status: ${qg.status}

Conditions:
${qg.conditions.collect { condition ->
    "- ${condition.metricKey}: ${condition.status} (Actual: ${condition.actualValue}, Threshold: ${condition.errorThreshold})"
}.join('\n')}

See full details at: ${env.BUILD_URL}"""

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
                                  --data "Subject: ${emailSubject}\\n\\n${emailBody}"
                        """

                        if (qg.status != 'OK') {
                            currentBuild.result = 'FAILURE'
                            error "Quality Gate failed"
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