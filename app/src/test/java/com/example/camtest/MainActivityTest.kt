package com.example.camtest

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File

class MainActivityTest {

    @Test
    fun `test getFile should create a temporary file`() {
        // Crear una instancia de MainActivity
        val activity = MainActivity()

        // Llamar al método que se quiere probar
        val file: File = activity.getFile("test_image")

        // Verificar que el archivo fue creado
        assertTrue(file.exists(), "El archivo debería existir")
        assertTrue(file.name.startsWith("test_image"), "El archivo debería empezar con 'test_image'")

        // Eliminar el archivo después de la prueba
        file.delete()
    }
}
