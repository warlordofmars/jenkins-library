def call(String inputText, String artifactsToArchive, String dockerBuilderArgs) {
    pipeline {
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
                agent {
                    dockerfile {
                        args dockerBuilderArgs
                    }
                }
                steps {
                    sh './gradlew build'
                }      
            }

            stage('test') {
                agent {
                    dockerfile {
                        args dockerBuilderArgs
                    }
                }
                steps {
                    sh './gradlew deploy'
                    input inputText
                }
            }

            stage('promote') {
                agent {
                    dockerfile {
                        args dockerBuilderArgs
                    }
                }
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