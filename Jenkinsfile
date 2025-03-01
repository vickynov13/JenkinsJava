node {
    def app

    stage('Clone repository') {
        checkout scm
    }

    stage('Docker Setup') {
        parallel(
          "Start Compose": {
            cmd-exec('echo ${jenkinsSU}')
    	    cmd_exec('sudo -S <<< ${jenkinsSU} docker-compose up -d --scale chrome=2 --scale firefox=0')
          },
          "Build Image": {
            cmd_exec('export MAVEN_HOME=/opt/apache-maven-3.9.9')
            cmd_exec('export PATH=$PATH:$MAVEN_HOME/bin')
            cmd_exec('mvn install')
          }
        )
    }

    stage('Execute') {
        catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {

       if (isUnix()) {
                mvn exec:java
            }
        else {
               mvn exec:java
           }
        }
    }

    stage('Docker Teardown') {
        parallel(
          "Stop Compose": {
            cmd_exec('docker-compose down --rmi local')
          }
        )
    }

//     stage('Create Report') {
//         /* Generate Allure Report */
//         allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
//     }

}

def cmd_exec(command) {
    if (isUnix()) {
        return sh(returnStdout: true, script: "${command}").trim()
    }
    else {
        return bat(returnStdout: true, script: "${command}").trim()
    }
}