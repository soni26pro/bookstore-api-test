pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 11'
    }

    environment {
        BASE_URL = 'http://localhost:8000'
        REPO = 'https://github.com/soni26pro/bookstore-api-test.git'
    }

    stages {

        stage('Checkout API (main branch)') {
            steps {
                dir('bookstore-api') {
                    git url: "${REPO}", branch: 'main'
                }
            }
        }

        stage('Start FastAPI Server') {
            steps {
                dir('bookstore-api') {
                    sh '''
                        python3 -m venv venv
                        source venv/bin/activate
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
                    git url: "${REPO}", branch: 'tests'
                }
            }
        }

        stage('Build and Run Tests') {
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
            echo 'Stopping FastAPI...'
            sh 'pkill -f uvicorn || true'
        }
    }
}
