package ru.nikitae57.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nikitae57.R
import ru.nikitae57.model.GraphPoint

class PathPointsAdapter(private val path: List<GraphPoint>)
    : RecyclerView.Adapter<PathPointsAdapter.PathPointViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PathPointViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.path_item, parent, false)

        return PathPointViewHolder(view)
    }

    override fun getItemCount() = path.size

    override fun onBindViewHolder(holder: PathPointViewHolder, position: Int) {
        val point = path[position]
        val isFirstItem = position == 0
        val isLastItem = position == path.size - 1

        holder.bind(point, isFirstItem, isLastItem)
    }

    inner class PathPointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val topLine = view.findViewById<View>(R.id.topLine)
        private val bottomLine = view.findViewById<View>(R.id.bottomLine)
        private val tvPointLabel = view.findViewById<TextView>(R.id.tvPointLabel)

        fun bind(point: GraphPoint, isFirstItem: Boolean = false, isLastItem: Boolean = false) {
            if (isFirstItem) {
                topLine.visibility = View.INVISIBLE
            } else {
                topLine.visibility = View.VISIBLE
            }

            if (isLastItem) {
                bottomLine.visibility = View.INVISIBLE
            } else {
                bottomLine.visibility = View.VISIBLE
            }

            tvPointLabel.text = point.name
        }
    }
}