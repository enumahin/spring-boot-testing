pipeline{
    agent any
    stages {
        stage('Building the application'){
            steps{
                sh 'rm target -R'
                sh 'mvn clean package'
            }
            post {
                success {
                    echo 'Archiving the artifact'
                    sh 'mv target/*.war target/restapi.war'
                    archiveArtifacts artifacts: 'target/restapi.war'
                }
            }
        }
        stage("Deploying to staging server"){
            steps{
                build job: 'Deploy_Application_Staging_Server'
            }
        }
        stage("Deploying to production server"){
            steps{
                timeout(time:5, unit:'DAYS'){
                    input message:'Approve Production Deployment?'
                }
                build job: 'Deploy_Application_Prod_Server'
            }
        }
    }
}