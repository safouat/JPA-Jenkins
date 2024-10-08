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
                    waitForQualityGate abortPipeline: true
                }
            }
        }

    }

        post {
            always {
                echo "Analyse terminée, vérifiez SonarQube pour les résultats."
            }

            failure {
                script {
                    emailext(
                        subject: "Pipeline Failed: ${env.JOB_NAME} ${env.BUILD_NUMBER}",
                        body: """<p>Bonjour,</p>
                                 <p>Le pipeline <strong>${env.JOB_NAME}</strong> a échoué à l'étape de Quality Gate lors de l'exécution de la build numéro <strong>${env.BUILD_NUMBER}</strong>.</p>
                                 <p>Statut de la Quality Gate: <strong>${currentBuild.result}</strong></p>
                                 <p>Vérifiez les détails de la build ici : <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                                 <p>Cordialement,</p>
                                 <p>Votre serveur Jenkins</p>""",
                        to: 'dounyagourja2@gmail.com', // Remplacez par les adresses souhaitées
                        mimeType: 'text/html'
                    )
                }
            }

            success {
                script {
                    emailext(
                        subject: "Pipeline Succeeded: ${env.JOB_NAME} ${env.BUILD_NUMBER}",
                        body: """<p>Bonjour,</p>
                                 <p>Le pipeline <strong>${env.JOB_NAME}</strong> s'est terminé avec succès à l'étape de Quality Gate lors de l'exécution de la build numéro <strong>${env.BUILD_NUMBER}</strong>.</p>
                                 <p>Statut de la Quality Gate: <strong>${currentBuild.result}</strong></p>
                                 <p>Vérifiez les détails de la build ici : <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                                 <p>Cordialement,</p>
                                 <p>Votre serveur Jenkins</p>""",
                        to: 'dounyagourja2@gmail.com', // Remplacez par les adresses souhaitées

                        mimeType: 'text/html'
                    )
                }
 }
 }

}