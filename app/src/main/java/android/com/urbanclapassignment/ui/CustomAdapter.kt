package android.com.urbanclapassignment.ui

import android.com.urbanclapassignment.DebouncedOnClickListener
import android.com.urbanclapassignment.R
import android.com.urbanclapassignment.model.ListItem
import android.com.urbanclapassignment.model.ListItem.Companion.DIFF_UTIL
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class TasksAdapter(private val adapterCallbackInterface: AdapterCallbackInterface) :
    ListAdapter<ListItem, TasksAdapter.TaskItemViewHolder>(DIFF_UTIL) {

    private val list = mutableListOf<ListItem>()

    fun addData(list: List<ListItem>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun filterList(filteredList: List<ListItem>) {
        this.list.addAll(filteredList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TaskItemViewHolder(view, adapterCallbackInterface)
    }

    inner class TaskItemViewHolder(view: View, callback: AdapterCallbackInterface) :
        RecyclerView.ViewHolder(view) {

        init {
            itemView.deleteButton.debouncedOnClick {
                val item = itemView.tag as ListItem
                callback.deleteClicked(item)
            }
            itemView.undoButton.debouncedOnClick {
                val item = itemView.tag as ListItem
                callback.undoClicked(item)
            }
            itemView.markDoneButton.debouncedOnClick {
                val item = itemView.tag as ListItem
                callback.markDoneClicked(item, adapterPosition)
            }
            itemView.debouncedOnClick {
                val item = itemView.tag as ListItem
                callback.taskClicked(item)
            }
        }

        fun bind(item: ListItem) {
            with(itemView) {
                tag = item
                if (item.done == 1) {
                    itemView.markDoneButton.isChecked = true
                    taskName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    undoButton.isVisible = true
                    deleteButton.isVisible = true
                    markDoneButton.isVisible = false
                } else {
                    itemView.markDoneButton.isChecked = false
                    taskName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    undoButton.isVisible = false
                    deleteButton.isVisible = false
                    markDoneButton.isVisible = true
                }
                taskName.text = item.taskName
            }
        }
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

interface AdapterCallbackInterface {
    fun undoClicked(task: ListItem)
    fun deleteClicked(task: ListItem)
    fun markDoneClicked(task: ListItem, pos: Int)
    fun taskClicked(task: ListItem)
}

inline fun View.debouncedOnClick(debounceTill: Long = 500, crossinline onClick: (v: View) -> Unit) {
    this.setOnClickListener(object : DebouncedOnClickListener(debounceTill) {
        override fun onDebouncedClick(v: View) {
            onClick(v)
        }
    })
}