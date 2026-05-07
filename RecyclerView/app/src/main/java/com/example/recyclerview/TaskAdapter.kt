package com.example.recyclerview

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onTaskClick: (Task) -> Unit,
    private val onTaskDelete: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView      = itemView.findViewById(R.id.cardView)
        val tvTitle: TextView       = itemView.findViewById(R.id.tvTitle)
        val tvCategory: TextView    = itemView.findViewById(R.id.tvCategory)
        val viewBanner: View        = itemView.findViewById(R.id.viewBanner)   // franja de color
        val viewPriority: View      = itemView.findViewById(R.id.viewPriority) // círculo chincheta
        val btnDelete: ImageButton  = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.tvTitle.text    = task.title
        holder.tvCategory.text = task.category

        if (task.isCompleted) {
            // ── Tarea completada: tarjeta atenuada con tachado ──
            val grayColor = "#9E9E9E".toColorInt()
            holder.viewBanner.setBackgroundColor(grayColor)
            holder.viewPriority.setBackgroundResource(R.drawable.circle_gray)
            holder.tvTitle.setTextColor("#9E9E9E".toColorInt())
            holder.tvTitle.paintFlags =
                holder.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.cardView.alpha = 0.72f
        } else {
            // ── Tarea pendiente: colores de madera + prioridad ──
            holder.tvTitle.setTextColor("#3E2723".toColorInt())
            holder.tvTitle.paintFlags =
                holder.tvTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.cardView.alpha = 1.0f

            when (task.priority) {
                Priority.HIGH -> {
                    holder.viewBanner.setBackgroundColor("#E53935".toColorInt())
                    holder.viewPriority.setBackgroundResource(R.drawable.circle_red)
                }
                Priority.MEDIUM -> {
                    holder.viewBanner.setBackgroundColor("#F9A825".toColorInt())
                    holder.viewPriority.setBackgroundResource(R.drawable.circle_yellow)
                }
                Priority.LOW -> {
                    holder.viewBanner.setBackgroundColor("#388E3C".toColorInt())
                    holder.viewPriority.setBackgroundResource(R.drawable.circle_green)
                }
            }
        }

        // Click en la nota: completar + Toast
        holder.cardView.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                val clickedTask = tasks[pos]
                clickedTask.isCompleted = !clickedTask.isCompleted
                notifyItemChanged(pos)
                onTaskClick(clickedTask)
            }
        }

        // Click en el botón eliminar
        holder.btnDelete.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                tasks.removeAt(currentPosition)
                notifyItemRemoved(currentPosition)
                onTaskDelete(currentPosition)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size
}
