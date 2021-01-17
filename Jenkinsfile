node {
  stage('Git Code Checkout') {
    git 'https://github.com/santosh-k-sh/hardware-custody'
  }
  stage('Compile-Package') {
    sh 'mvn package'
  }
}
