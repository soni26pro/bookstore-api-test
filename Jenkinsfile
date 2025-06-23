pipeline {
    agent any

    tools {
        // Assuming Maven and JDK are configured in Jenkins Global Tool Configuration
        maven 'Maven 3' // Replace 'Maven 3' with the name of your Maven tool configuration in Jenkins
        jdk 'JDK 8'   // Replace 'JDK 8' with the name of your JDK tool configuration in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Assuming your Jenkins job is configured to checkout from a Git repository
                // If not, you might need a 'git' step here, e.g.:
                // git url: 'your_repository_url', branch: 'main'
                script {
                    echo 'Source code checked out by Jenkins job configuration.'
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Report') {
            steps {
                echo 'Generating and publishing Allure report...'
                // Ensure Allure plugin is installed and configured in Jenkins
                // The 'allure' step requires the path to the allure-results directory
                script {
                    if (fileExists('allure-results')) {
                        allure([
                            includeProperties: false,
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'allure-results']]
                        ])
                    } else {
                        echo 'allure-results directory not found. Skipping Allure report publication.'
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
            // Send a generic notification always
            // Requires Jenkins Slack plugin configured with a default channel or webhook
            // slackSend message: "Pipeline ${currentBuild.fullDisplayName} finished with status ${currentBuild.currentResult}"
        }
        failure {
            echo 'Pipeline failed.'
            // Send a failure notification
            // Requires Jenkins Slack plugin configured
            slackSend color: 'danger', message: "Pipeline ${currentBuild.fullDisplayName} failed! Check build log for details. ${env.BUILD_URL}"
        }
        success {
            echo 'Pipeline succeeded.'
            // Send a success notification
            // Requires Jenkins Slack plugin configured
            slackSend color: 'good', message: "Pipeline ${currentBuild.fullDisplayName} succeeded! Allure Report: ${env.BUILD_URL}allure/"
        }
    }
}
