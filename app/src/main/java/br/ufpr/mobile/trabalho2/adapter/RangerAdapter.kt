package br.ufpr.mobile.trabalho2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import br.ufpr.mobile.trabalho2.R
import br.ufpr.mobile.trabalho2.model.PRSeries

class RangerAdapter(
    private val rangers: List<PRSeries>,
    private val click: (PRSeries) -> Unit
): Adapter<RangerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rangerImg = itemView.findViewById<ImageView>(R.id.imgPR)
        val rangerName = itemView.findViewById<TextView>(R.id.textNome)
        val rangerAno = itemView.findViewById<TextView>(R.id.textAno)

        fun bind(ranger: PRSeries){
            rangerName.text = ranger.nome
            rangerAno.text = ranger.ano.toString()
            rangerImg.setImageResource(ranger.image.toInt())
            itemView.setOnClickListener{click(ranger)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RangerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RangerAdapter.ViewHolder, position: Int) {
        holder.bind(rangers[position])
    }

    override fun getItemCount(): Int {
        return rangers.size
    }

}