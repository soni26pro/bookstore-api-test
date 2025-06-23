pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 11'
    }

    environment {
        REPO = 'https://github.com/soni26pro/bookstore-api-test.git'
        BASE_URL = 'http://localhost:8000'
    }

    stages {
        stage('Checkout FastAPI (main branch)') {
            steps {
                dir('bookstore-api') {
                    git branch: 'main', url: "${REPO}"
                }
            }
        }

        stage('Start FastAPI Server') {
            steps {
                dir('bookstore-api') {
                    sh '''
                        python3 -m venv venv
                        source venv/bin/activate
                        pip install --upgrade pip
                        pip install -r requirements.txt
                        nohup venv/bin/uvicorn main:app --host 0.0.0.0 --port 8000 &
                        sleep 5
                    '''
                }
            }
        }

        stage('Checkout Tests (tests branch)') {
            steps {
                dir('bookstore-tests') {
                    git branch: 'tests', url: "${REPO}"
                }
            }
        }

        stage('Run Tests') {
            steps {
                dir('bookstore-tests') {
                    sh "mvn clean test -Dapi.base.url=${BASE_URL}"
                }
            }
        }

        stage('Allure Report') {
            steps {
                dir('bookstore-tests') {
                    script {
                        if (fileExists('allure-results')) {
                            allure([
                                includeProperties: false,
                                reportBuildPolicy: 'ALWAYS',
                                results: [[path: 'allure-results']]
                            ])
                        } else {
                            echo 'No allure-results found'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'pkill -f uvicorn || true'
        }
        success {
            echo 'Pipeline succeeded.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
