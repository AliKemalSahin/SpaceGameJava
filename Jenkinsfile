pipeline 
{
    agent any
    environment {
        DOCKER_IMAGE_NAME = "alikemal/denemess"
    }
    tools 
    {
        maven 'M3'
    }
    stages 
    {
        when {
                branch 'master'
             }
        stage('Build Jar')
        {
            steps 
            {
                sh '''
                 mvn clean package
                 cd target
                 cp SpaceGame-0.0.1-SNAPSHOT.jar SpaceGame.jar 
                '''
                stash includes: 'target/*.jar', name: 'targetfiles'
            }
        }
        stage('Build Docker Image') 
        {
            steps 
            {
                script 
                {
                    app = docker.build(DOCKER_IMAGE_NAME) 
                }
            }
        }
    }
}
