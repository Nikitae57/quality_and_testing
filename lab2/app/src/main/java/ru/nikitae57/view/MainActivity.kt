package ru.nikitae57.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.nikitae57.R
import ru.nikitae57.model.Edge
import ru.nikitae57.model.GraphPoint
import ru.nikitae57.viewmodel.graph.GraphViewModel
import ru.nikitae57.viewmodel.graph.GraphViewModelFactory
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GraphViewModel
    private lateinit var edgesRecyclerViewAdapter: GraphEdgesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        setListeners()
        initRecyclerView()
    }

    private fun initViewModel() {
        val factory = GraphViewModelFactory(application)
        val thisActivity = this
        viewModel = ViewModelProvider(this, factory).get(GraphViewModel::class.java)
        viewModel.apply {
            graphLiveData.observe(thisActivity, Observer { graph ->
                graph?.let {
                    edgesRecyclerViewAdapter = GraphEdgesAdapter(it)
                    rvGraphPoints.adapter = edgesRecyclerViewAdapter
                }

                elementAddedEvent.observe(thisActivity, Observer { elementNumber ->
                    elementNumber?.let {
                        edgesRecyclerViewAdapter.notifyItemInserted(elementNumber)
                        rvGraphPoints.scrollToPosition(elementNumber)
                        btnFindShortestWay.apply {
                            if (edgesRecyclerViewAdapter.itemCount > 1) {
                                visibility = View.VISIBLE
                            }
                        }

                        elementAddedEvent.postValue(null)
                    }
                })
            })
        }
    }

    private fun setListeners() {
        btnAddGraphPoint.setOnClickListener(AddEdgeBtnListener())
    }

    private fun initRecyclerView() {
        rvGraphPoints.layoutManager = LinearLayoutManager(this)
    }

    inner class AddEdgeBtnListener : View.OnClickListener {
        private fun validateInputFields(): Boolean {
            var isAllValid = true

            val x1Str = etX1.text.toString()
            val y1Str = etY1.text.toString()
            val x2Str = etX2.text.toString()
            val y2Str = etY2.text.toString()

            // Check if all points are given
            val inputFields = listOf(etX1, etX2, etY1, etY2)

            val emptyFields = inputFields.filter { it?.text.isNullOrEmpty() }
            if (emptyFields.isNotEmpty()) {
                emptyFields.forEach { it.error = "Ввдите значение" }
                isAllValid = false
            }

            // Only distance is empty
            if (emptyFields.isEmpty()) {
                val x1 = x1Str.toInt()
                val y1 = y1Str.toInt()
                val x2 = x2Str.toInt()
                val y2 = y2Str.toInt()

                if (x1 == x2 && y1 == y2) {
                    etX1.requestFocus()
                    isAllValid = false
                    etX1.error = "Введиет разные вершины"
                }
            }
            try {
                inputFields.first { !it.error.isNullOrEmpty() }.requestFocus()
            } catch (noElementEx: NoSuchElementException) {}

            return isAllValid
        }

        override fun onClick(v: View?) {
            val isAllFieldsValid = validateInputFields()
            if (!isAllFieldsValid) { return }

            val x1 = etX1.text.toString().toInt()
            val y1 = etY1.text.toString().toInt()
            val x2 = etX2.text.toString().toInt()
            val y2 = etY2.text.toString().toInt()

            val point1Name = viewModel.getPointName(x1, y1)
            val point2Name = viewModel.getPointName(x2, y2)
            val point1 = GraphPoint(x1, y1, point1Name)
            val point2 = GraphPoint(x2, y2, point2Name)
            val distance = sqrt(
                (x1 - x2).toDouble().pow(2)
                   + (y1 - y2).toDouble().pow(2)
            )

            val edge = Edge(point1, point2, distance)
            viewModel.addEdge(edge)
        }
    }
}
