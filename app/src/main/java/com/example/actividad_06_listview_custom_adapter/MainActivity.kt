package com.example.actividad_06_listview_custom_adapter

import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

// --- Clase principal de la actividad ---

class MainActivity : AppCompatActivity() {

    // --- Declaración de variables para el ListView, el adaptador, la lista de contactos y barra de búsqueda ---

    private lateinit var listView: ListView
    private lateinit var adapter: ContactAdapter
    private lateinit var contactos: MutableList<ContactoModel>
    private lateinit var searchView: SearchView

    // --- Método que genera la actividad por default ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // --- Inicialización del ListView y la barra de búsqueda ---

        listView = findViewById(R.id.listViewContactos)
        searchView = findViewById(R.id.searchView)

        // --- Creación de la lista de contactos con ejemplos ---

        contactos = mutableListOf(
            ContactoModel("Derek Silva", "5549419426", "derekenid67@gmail.com", R.drawable.derek),
            ContactoModel("Amadeo Zavala", "5625951451", "amadeozavala2015@gmail.com", R.drawable.amadeo),
            ContactoModel("Marisol Cruz", "5621096745", "marisolcruz.45@gmail.com", R.drawable.marisol),
            ContactoModel("Eric Zg", "5560637206", "ericzg.1987@gmail.com", R.drawable.eric),
            ContactoModel("Clarita", "5636201397", "aideclh.23@gmail.com", R.drawable.clarita)
        )

        // --- Inicialización del adaptador con la lista de contactos ---

        adapter = ContactAdapter(this, contactos)
        listView.adapter = adapter  // --- Asignación del adaptador al ListView ---

        // --- Configuración del listener para la barra de búsqueda ---

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false  // --- No se realiza ninguna acción al enviar el texto ---
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                // --- Filtrar la lista de contactos según el texto ingresado ---

                adapter.filter(newText ?: "")
                return true  // --- Indica que el evento es verdadero ---
            }
        })
    }
}