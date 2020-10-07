pipeline 
{
    agent any
    environment {
        DOCKER_IMAGE_NAME = "alikemal/deneme"
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
                 cd dist
                 cp SpaceGame-0.0.1-SNAPSHOT.jar SpaceGame.jar 
                '''
                stash includes: 'dist/*.jar', name: 'distfiles'
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
