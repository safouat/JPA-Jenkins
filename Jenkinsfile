pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/aynuod/JPA-Jenkins.git', branch: 'main'
            }
        }

        // stage('Build') {
        //     steps {
        //         sh 'mvn clean install'
        //     }
        // }

        // stage('SonarQube Analysis') {
        //     steps {
        //         withSonarQubeEnv('SonarQube') {
        //             sh 'mvn sonar:sonar \
        //             -Dsonar.projectKey=spring_dounya \
        //             -Dsonar.host.url=http://localhost:9000 \
        //             -Dsonar.login=sqa_1868c1341b4f3e169077c609a98f0637f11ee3b3'
        //         }
        //     }
        // }

        stage('SonarQube Analysis') {
            steps {
                script {
                    def scannerHome = tool 'sonar-scanner'
                    withSonarQubeEnv('SonarQube') {
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=spring_dounya2 \
                            -Dsonar.host.url=https://localhost:9000 \
                            -Dsonar.login=sqp_c42854901308a8b594be233bafa1f87abeccb71b \
                            -Dsonar.sources=./src \
                            -Dsonar.exclusions=/*.java \
                        """
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

    }

}