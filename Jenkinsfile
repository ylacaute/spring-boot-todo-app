node {

  stage('Build') {
    def mvnHome = tool 'maven-3'

    echo "[${env.JOB_NAME}] Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
    checkout scm
    sh "${mvnHome}/bin/mvn clean install -P prod"
  }

  stage('Test') {
    echo 'Test'
  }

  stage('Deploy') {
    echo 'Deploy'
  }

}
