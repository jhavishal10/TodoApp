package android.com.urbanclapassignment.ui

import android.com.urbanclapassignment.DebouncedOnClickListener
import android.com.urbanclapassignment.R
import android.com.urbanclapassignment.model.ListItem
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.IpoCompanyBusinessViewHolder>() {

    private val list = mutableListOf<ListItem>()

    fun addData(list: List<ListItem>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IpoCompanyBusinessViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return IpoCompanyBusinessViewHolder(view)

    }

    inner class IpoCompanyBusinessViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.deleteButton.debouncedOnClick {

            }
            itemView.undoButton.debouncedOnClick {

            }
            itemView.markDoneButton.debouncedOnClick {

            }
        }
        fun bind(item: ListItem) {
            with(itemView) {
                if(item.done){
                    taskName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    undoButton.isVisible = true
                    deleteButton.isVisible = true
                    markDoneButton.isVisible = false
                }else {
                    undoButton.isVisible = false
                    deleteButton.isVisible = false
                    markDoneButton.isVisible = true
                }
                taskName.text = item.taskName

            }
        }
    }

    override fun onBindViewHolder(holder: IpoCompanyBusinessViewHolder, position: Int) {
        holder.bind(list[position])
    }

}

inline fun View.debouncedOnClick(debounceTill: Long = 500, crossinline onClick: (v: View) -> Unit) {
    this.setOnClickListener(object : DebouncedOnClickListener(debounceTill) {
        override fun onDebouncedClick(v: View) {
            onClick(v)
        }
    })
}