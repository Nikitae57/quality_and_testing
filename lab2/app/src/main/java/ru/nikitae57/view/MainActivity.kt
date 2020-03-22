package ru.nikitae57.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.choose_points_layout.*
import kotlinx.android.synthetic.main.enter_graph_content.*
import kotlinx.android.synthetic.main.shortest_way_content.*
import ru.nikitae57.R
import ru.nikitae57.viewmodel.graph.GraphViewModel
import ru.nikitae57.viewmodel.graph.GraphViewModelFactory

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
            // Init recycler view adapter
            edgesRecyclerViewAdapter = GraphEdgesAdapter(edges)
            rvGraphPoints.adapter = edgesRecyclerViewAdapter

            elementAddedEvent.observe(thisActivity, Observer { elementNumber ->
                elementNumber?.let {
                    edgesRecyclerViewAdapter.notifyItemInserted(elementNumber)
                    rvGraphPoints.scrollToPosition(elementNumber)
                    btnSelectPoints.apply {
                        if (edgesRecyclerViewAdapter.itemCount > 1) {
                            visibility = View.VISIBLE
                        }
                    }

                    elementAddedEvent.postValue(null)
                }
            })
        }
    }

    private fun setListeners() {
        btnAddGraphPoint.setOnClickListener(AddEdgeBtnListener())
        btnSelectPoints.setOnClickListener {
            val initSpinner = { picker: NumberPicker ->
                picker.apply {
                    minValue = 0
                    maxValue = viewModel.getPointsCount() - 1 // Inclusive
                    displayedValues = viewModel.getPointsNames().toTypedArray()
                }
            }

            initSpinner(npFirstPoint)
            initSpinner(npSecondPoint)
            hideKeyboard()
            vfRootView.displayedChild = 1
        }

        btnFindShortestWay.setOnClickListener {
            val point1 = viewModel.getPoint(npFirstPoint.value)
            val point2 = viewModel.getPoint(npSecondPoint.value)
            if (point1 == point2) {
                longToast("Выберите разные вершины")
                return@setOnClickListener
            }

            val way = viewModel.findShortestWay(point1, point2)
            if (way == null) {
                longToast("Пути между вершинами нет")
                return@setOnClickListener
            }

            tvShortestWayHeader.text = getString(R.string.shortest_way_from_X_to_X, point1.name, point2.name)
            tvShortestWay.text = way
            vfRootView.displayedChild = 2
        }
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

            viewModel.addEdge(x1, y1, x2, y2)
        }
    }
}
