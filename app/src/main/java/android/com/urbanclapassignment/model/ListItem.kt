package android.com.urbanclapassignment.model

import androidx.recyclerview.widget.DiffUtil

data class ListItem(
    val taskId: Long,
    val taskName: String,
    val done: Int,
    val priority: Int
) {
    companion object {

        val DIFF_UTIL = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem.taskId == newItem.taskId
            }

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}