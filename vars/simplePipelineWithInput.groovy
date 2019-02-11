def call(inputText, artifactsToArchive) {
    pipeline {
        agent any
        environment {
            PATH = "/usr/local/bin:$PATH"
        }
        stages {

            stage('version') {
                when {
                    branch 'master'
                }
                steps {
                    sh './gradlew release -Prelease.disableChecks -Prelease.pushTagsOnly'
                } 
            }

            stage('build') {
                steps {
                    sh './gradlew build'
                }      
            }

            stage('test') {
                steps {
                    sh './gradlew deploy'
                    input inputText
                }
            }

            stage('promote') {
                environment {
                    PROMOTE = true
                }
                when {
                    branch 'master'
                }
                steps {
                    sh './gradlew deploy'
                    // sh './gradlew print'
                }
            }

        }

        post {
            always {
                archiveArtifacts artifacts: artifactsToArchive, fingerprint: true
                junit 'build/report.xml'
            }
        }

    }
}