node {

  stage('Build') {
    echo 'Build'
    def mvnHome = tool 'M3'

    checkout scm
    sh "${mvnHome}/bin/mvn package -P ci"
  }

  stage('Test') {
    echo 'Test'
    def mvnHome = tool 'M3'

    checkout scm
    sh "${mvnHome}/bin/mvn install -P ci"
  }

  stage('Deploy') {
    echo 'Deploy'
  }

}
