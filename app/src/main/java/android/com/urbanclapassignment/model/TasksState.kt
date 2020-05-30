package android.com.urbanclapassignment.model

sealed class TasksState {
    object Clear : TasksState()
    data class Data(val list: List<ListItem>) : TasksState()
    data class NotifyItemChange(val pos: Int) : TasksState()
    data class NotifyItemRemoved(val pos: Int) : TasksState()
    data class NotifyItemInserted(val pos: Int) : TasksState()

}