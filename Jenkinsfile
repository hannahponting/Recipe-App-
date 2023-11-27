pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
         stage('Test') {
             steps {
                 sh 'mvn test'
             }
             post {
                 always {
                      junit 'target/surefire-reports/*.xml'
                 }
             }
         }

                 stage('SonarQube Analysis') {
                             steps {
                                 script {
                                     withSonarQubeEnv('sonarqube') {
                 //                        sh "${tool('sonar-scanner')}/bin/sonar-scanner -Dsonar.projectKey=myProjectKey -Dsonar.projectName=myProjectName"
                                         sh 'mvn clean package sonar:sonar'
                                     }
                                 }
                             }
                         }

                 stage("Quality Gate") {
                     steps {
                       timeout(time: 15, unit: 'MINUTES') {
                         waitForQualityGate abortPipeline: false
                       }
                     }
                   }


         stage('Deliver') {
             steps {
             echo 'Deployment Successful!!'
                // sh './jenkins/scripts/deliver.sh'
             }
        }
    }
}