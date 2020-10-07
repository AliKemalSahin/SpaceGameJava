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
                 cd dist
                 cp SpaceGame-0.0.1-SNAPSHOT.jar SpaceGame.jar 
                '''
                stash includes: 'dist/*.jar', name: 'distfiles'
            }
        }
        stage('Build Docker Image') 
        {
            when {
                branch 'master'
            }
            steps 
            {
                script 
                {
                    sh 'docker version'
                    app = docker.build(DOCKER_IMAGE_NAME) 
                }
            }
        }
        stage('Push Docker Image') {
            when {
                branch 'master'
            }
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker_hub_login') {
                        app.push("${env.BUILD_NUMBER}")
                        app.push("latest")
                    }
                }
            }
        }
    }
}
