node {
    def app

    stage('Clone repository') {
        checkout scm
    }

    stage('Docker Setup') {
        parallel(
          "Build Image": {
            script {
                sh '''#!/bin/bash
                export MAVEN_HOME=/opt/apache-maven-3.9.9
                export PATH=$PATH:$MAVEN_HOME/bin
                mvn -version
                mvn install
                '''
            }
          }
        )
    }

    stage('Execute') {
        catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
            script {
                if (isUnix()) {
                    sh '''#!/bin/bash
                    export MAVEN_HOME=/opt/apache-maven-3.9.9
                    export PATH=$PATH:$MAVEN_HOME/bin
                    mvn exec:java
                    '''
                } else {
                    bat 'mvn exec:java'
                }
            }
        }
    }

    // Uncomment and modify this if you need a report generation stage.
    /*
    stage('Create Report') {
        allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
    }
    */
}

def cmd_exec(command) {
    if (isUnix()) {
        return sh(returnStdout: true, script: "${command}").trim()
    } else {
        return bat(returnStdout: true, script: "${command}").trim()
    }
}
