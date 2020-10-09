pipeline 
{
    agent any
    environment {
        DOCKER_IMAGE_NAME = "alikemal/spacegame"
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
        
        stage('Orchestrate') {
            steps{
                script{
                    sh 'helm create helm-myapp'
                    sh 'helm package helm-myapp'
                    sh 'helm install helm-myapp-0.1.0.tgz --generate-name'
                }
                }
        }   
        
        stage('DeployToProduction') {
            when {
                branch 'master'
            }
            steps {
                input 'Deploy to Production?'
                milestone(1)
                kubernetesDeploy(
                    kubeconfigId: 'kubeconfig',
                    configs: 'kuber.yaml',
                    enableConfigSubstitution: true
                )
            }
        }
    }
}
