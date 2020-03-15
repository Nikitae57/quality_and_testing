package ru.nikitae57.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nikitae57.R
import ru.nikitae57.model.Edge

class GraphEdgesAdapter(private val graphEdges: List<Edge>)
    : RecyclerView.Adapter<GraphEdgesAdapter.GraphEdgeHolder>() {

    inner class GraphEdgeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ctx = view.context

        private val tvCoords1 = view.findViewById<TextView>(R.id.tvCoords1)
        private val tvCoords2 = view.findViewById<TextView>(R.id.tvCoords2)
        private val tvDistance = view.findViewById<TextView>(R.id.tvDistance)

        fun bind(edge: Edge) {
            edge.run {
                point1.run {
                    tvCoords1.text = ctx.getString(R.string.edge_item_coords,  point1.name, x, y)
                }
                point2.run {
                    tvCoords2.text = ctx.getString(R.string.edge_item_coords, point2.name, x, y)
                }

                // It's int
                if (distance - distance.toInt() == 0.0) {
                    tvDistance.text = ctx.getString(R.string.edge_item_distance_int, distance.toInt())
                } else {
                    tvDistance.text = ctx.getString(R.string.edge_item_distance_double, distance)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphEdgeHolder {
        val holderView = LayoutInflater.from(parent.context)
            .inflate(R.layout.graph_edge_item, parent, false)

        return GraphEdgeHolder(holderView)
    }

    override fun getItemCount(): Int {
        return graphEdges.size
    }

    override fun onBindViewHolder(holder: GraphEdgeHolder, position: Int) {
        val edge = graphEdges[position]
        holder.bind(edge)
    }
}