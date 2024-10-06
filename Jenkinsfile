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
                    bat './mvnw sonar:sonar \
                    -Dsonar.projectKey=spring-test \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.login=sqp_f70d1c126c922be5d6465ea03e2d1440d5ae8303'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            currentBuild.result = 'FAILURE'
                            emailext(
                                subject: "SonarQube Quality Gate failed for ${env.JOB_NAME}",
                                body: """The SonarQube analysis has failed the Quality Gate.
                                See details at ${env.BUILD_URL}""",
                                to: 'dounyagourja2@gmail.com'
                            )
                        } else {
                            emailext(
                                subject: "SonarQube Quality Gate passed for ${env.JOB_NAME}",
                                body: """The SonarQube analysis has passed the Quality Gate.
                                See details at ${env.BUILD_URL}""",
                                to: 'dounyagourja2@gmail.com'
                            )
                        }
                    }
                }
            }
        }
    }
}
