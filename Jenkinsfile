pipeline {
  agent any
  stages {
    stage('Set Variable') {
      steps {
        script {
          IMAGE_NAME = "ubuntu-18.04"
          IMAGE_STORAGE = "judo-container-image.kr.ncr.ntruss.com"
          IMAGE_STORAGE_CREDENTIAL = "Container Registry 접근 Credential id"
          SSH_CONNECTION = "root@106.10.43.96"
          SSH_CONNECTION_CREDENTIAL = "Deploy-Server-SSH-Credential"
        }

      }
    }

    stage('Clean Build Test') {
      steps {
        sh './gradlew clean build test'
      }
    }

    stage('Build Container Image') {
      steps {
        script {
          image = docker.build("${IMAGE_STORAGE}/${IMAGE_NAME}")
        }

      }
    }

    stage('Push Container Image') {
      steps {
        script {
          docker.withRegistry("https://${IMAGE_STORAGE}", IMAGE_STORAGE_CREDENTIAL) {
            image.push("${env.BUILD_NUMBER}")
            image.push("latest")
            image
          }
        }

      }
    }

    stage('Server Run') {
      steps {
        sshagent(credentials: [SSH_CONNECTION_CREDENTIAL]) {
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rm -f ${IMAGE_NAME}'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker rmi -f ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker pull ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker images'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker run -d --name ${IMAGE_NAME} -p 8080:8080 ${IMAGE_STORAGE}/${IMAGE_NAME}:latest'"
          sh "ssh -o StrictHostKeyChecking=no ${SSH_CONNECTION} 'docker ps'"
        }

      }
    }

  }
}