def call() {
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

            stage('promote') {
            environment {
                PROMOTE = true
            }
            when {
                branch 'master'
            }
            steps {
                sh './gradlew deploy -s'
            }
            }

        }

    }
}