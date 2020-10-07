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
                    chmod 777 /var/run/docker.sock
                    app = docker.build(DOCKER_IMAGE_NAME) 
                }
            }
        }
    }
}
