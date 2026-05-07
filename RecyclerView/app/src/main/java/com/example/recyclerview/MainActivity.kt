package com.example.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Datos de ejemplo con distintas prioridades
        taskList.addAll(
            listOf(
                Task(1, "Entregar informe mensual",   "Trabajo",   Priority.HIGH),
                Task(2, "Revisar correos pendientes", "Trabajo",   Priority.MEDIUM),
                Task(3, "Comprar ingredientes",       "Personal",  Priority.LOW),
                Task(4, "Llamar al médico",           "Salud",     Priority.HIGH),
                Task(5, "Actualizar documentación",   "Proyecto",  Priority.MEDIUM),
                Task(6, "Hacer ejercicio",            "Salud",     Priority.LOW),
                Task(7, "Preparar presentación",      "Trabajo",   Priority.HIGH),
                Task(8, "Leer libro de Kotlin",       "Formación", Priority.LOW),
                Task(9, "Pagar facturas",             "Finanzas",  Priority.MEDIUM),
                Task(10,"Organizar escritorio",       "Personal",  Priority.LOW)
            )
        )

        // Ordenar: ALTA (rojo) → MEDIA (amarillo) → BAJA (verde)
        taskList.sortBy { it.priority.ordinal }

        adapter = TaskAdapter(
            tasks = taskList,
            onTaskClick = { task ->
                // El adaptador ya cambió isCompleted y llamó a notifyItemChanged
                Toast.makeText(this, "Completando... ${task.title}", Toast.LENGTH_SHORT).show()
            },
            onTaskDelete = { _ ->
                Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}



