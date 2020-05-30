package android.com.urbanclapassignment.ui

import android.com.urbanclapassignment.R
import android.com.urbanclapassignment.model.ListItem
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val adapter = TasksAdapter()
//    private lateinit var viewModel: ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
//        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
//
//        viewModel.users.observe(this, Observer { tasks: List<ListItem> ->
//            adapter.addData(tasks)
//
//        })
        initUi()
    }

    private fun initUi() {
        val task = mutableListOf<ListItem>()
        val listItem1 =ListItem("asrdytubjnobhgcftygvhbigvcfdtxrtcgvubiyvcdxrsdfybiunbyvtcxrsdcybuivtcrdxcfvgybuitvcxrsdcfvgbyvxsezxdcfvgbuhigvxsezxdcfvgbuhsxedcgbuhnbgxsedcgbuhnbfcdxsedcfvgbuhnijhbgvxsdf",true)
        val listItem2 =ListItem("asfcgvhjbkdf",true)
        val listItem3 =ListItem("afcygvhetdrxcyvuybhijnhvgcfxdtzsrdxtfcygvbhjnbhvgcfxdzsdxfcgvhbjhvgcfxdfzsdxcgvhbjnobuyvutcrxtezrsxtrcyuvihoijhugyfutydrtezrtxryctugiuhogiyftudyrteszrtrytugiuhogiyftdyrstezrwtxrytfuygiuyftudrystezrtrxdytfuygiuhogiyfutdyrsterzwbjksdf",false)
        val listItem4 =ListItem("gycvhbjn",true)
        task.add(listItem1)
        task.add(listItem2)
        task.add(listItem3)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        task.add(listItem4)
        adapter.addData(task)
        taskRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        taskRecyclerView.adapter = adapter
    }
}