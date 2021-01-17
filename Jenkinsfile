node {
  stage('Git Code Checkout') {
    git 'https://github.com/santosh-k-sh/hardware-custody'
  }
  stage('Compile-Package') {
    // Getting Maven Home
    def MAVEN_HOME = tool name: 'maven 3.6', type: 'maven'

    sh "${MAVEN_HOME}/bin/mvn package"
  }
  
  stage('Email Notification') {
    mail bcc: '', body: '''Hi Santosh,

    Jenkins mail alert.

    Thanks''', cc: '', from: '', replyTo: '', subject: 'Jenkins Job', to: 'santosh_k_sh@live.com'
  }
}
