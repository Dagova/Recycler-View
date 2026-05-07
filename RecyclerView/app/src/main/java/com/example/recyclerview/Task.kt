package com.example.recyclerview

enum class Priority {
    HIGH,    // Rojo
    MEDIUM,  // Amarillo
    LOW      // Verde
}

data class Task(
    val id: Int,
    val title: String,
    val category: String,
    val priority: Priority,
    var isCompleted: Boolean = false
)

