package com.example.actividad_06_listview_custom_adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

// --- Esta clase es el adaptador que conecta nuestros datos (contactos) con el ListView ---

class ContactAdapter(private val context: Context, private var contactos: List<ContactoModel>) : BaseAdapter() {

    // --- Aquí guardamos los contactos que vamos a mostrar ---

    private var contactosFiltrados: List<ContactoModel> = contactos.toMutableList()

    // --- Este método nos dice cuántos contactos hay para mostrar ---

    override fun getCount(): Int {
        return contactosFiltrados.size
    }

    // --- Este método devuelve el contacto que está en la posición que le pedimos ---

    override fun getItem(position: Int): Any {
        return contactosFiltrados[position]
    }

    // --- Este método devuelve un ID para el contacto en la posición que le pedimos ---

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // --- Aquí se crea la vista que vamos a mostrar para cada contacto ---

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // --- Si no tenemos una vista reciclada, creamos una nueva ---

        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false)

        // --- Obtenemos los elementos de la vista (imagen, nombre, teléfono, email, botón) ---

        val imageViewProfile = view.findViewById<ImageView>(R.id.imageViewProfile)
        val textViewNombre = view.findViewById<TextView>(R.id.textViewNombre)
        val textViewTelefono = view.findViewById<TextView>(R.id.textViewTelefono)
        val textViewEmail = view.findViewById<TextView>(R.id.textViewEmail)
        val buttonEmail = view.findViewById<Button>(R.id.buttonEmail)

        // --- Obtenemos el contacto que vamos a mostrar ---

        val contacto = contactosFiltrados[position]

        // --- Asignamos la información del contacto a los elementos de la vista ---

        imageViewProfile.setImageResource(contacto.imagenId)
        textViewNombre.text = "Nombre: ${contacto.nombre}"
        textViewTelefono.text = "Teléfono: ${contacto.telefono}"
        textViewEmail.text = "Correo: ${contacto.email}"

        // --- Configuramos el botón para enviar un correo ---

        buttonEmail.setOnClickListener {

            // --- Creamos un intento para enviar un correo ---

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${contacto.email}")  // --- Aquí va la dirección de correo ---
                putExtra(Intent.EXTRA_SUBJECT, "Hola ${contacto.nombre}")  // --- Aquí va el asunto del correo ---
            }

            // --- Verificamos que haya una aplicación que pueda enviar el correo ---

            if (intent.resolveActivity(context.packageManager) != null) {
                ContextCompat.startActivity(context, intent, null)  // --- Abrimos la aplicación de correo ---
            }
        }

        // --- Verificamos si el contacto es duplicado (mismo teléfono) ---

        val isDuplicate = contactos.count { it.telefono == contacto.telefono } > 1

        // --- Cambiamos el color de fondo si el contacto es duplicado ---

        if (isDuplicate) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.duplicate_contact_background)) // --- Color para duplicados ---
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.default_contact_background)) // --- Color por defecto ---
        }

        return view  // --- Devolvemos la vista que hemos creado ---
    }

    // --- Este método se usa para actualizar la lista de contactos ---

    fun updateList(newList: List<ContactoModel>) {
        contactos = newList  // --- Actualizamos la lista ---
        contactosFiltrados = newList.toMutableList()  // --- Hacemos una copia de la nueva lista ---
        notifyDataSetChanged()  // --- Notificamos que la lista ha cambiado ---
    }

    // --- Este método filtra los contactos según lo que se escribe en la búsqueda ---

    fun filter(query: String) {
        contactosFiltrados = if (query.isEmpty()) {
            contactos  // --- Si no hay nada en la búsqueda, mostramos todos los contactos ---
        } else {
            // --- Si hay texto en la búsqueda, filtramos los contactos que coinciden ---
            contactos.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.telefono.contains(query) ||
                        it.email.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()  // --- Notificamos que la lista filtrada ha cambiado ---
    }
}
