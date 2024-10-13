package com.example.actividad_06_listview_custom_adapter

// --- Clase que representa el modelo del contacto ---

/* --- La palabra reservada "data" en Kotlin se utiliza para definir una data class,
       que es un tipo especial de clase que está diseñada principalmente para almacenar datos. */

data class ContactoModel(

    val nombre: String, // Nombre del contacto
    val telefono: String, // Número de teléfono del contacto
    val email: String, // Correo electrónico del contacto
    val imagenId: Int // ID de la imagen de perfil del contacto

)
