pipeline {
    agent any

    environment {
        ANDROID_HOME = "$HOME/Library/Android/sdk"
        PATH = "$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"
    }

    stages {
        stage('Clonar Código') {
            steps {
                git branch: 'main', git 'https://github.com/EstuardOrt/Camtest'
            }
        }

        stage('Compilar Proyecto') {
            steps {
                sh './gradlew assembleDebug'
            }
        }

        stage('Ejecutar Pruebas Unitarias') {
            steps {
                sh './gradlew testDebugUnitTest'
            }
        }

        stage('Publicar Reporte de Pruebas') {
            steps {
                junit '**/build/test-results/testDebugUnitTest/*.xml'
            }
        }
    }
}
