package com.toeichero.project.View.Home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toeichero.project.Model.userRankAccount
import com.toeichero.project.R
import kotlinx.android.synthetic.main.rank_list.view.*

class RankAdapter(private var rankList: List<userRankAccount>, private var namaActive: String): RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val image: ImageView = item.rankImg
        val name: TextView = item.username
        val rank:TextView = item.rank
        val score: TextView = item.score
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rank_list, parent, false)
        return RankAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RankAdapter.ViewHolder, position: Int) {
        holder.rank.text = (position + 1).toString()
        holder.name.text = rankList[position].username
        if(namaActive == rankList[position].username){
            holder.name.setTextColor(Color.parseColor("#FFF84B"))
        }else{
            holder.name.setTextColor(Color.parseColor("#FFFFFF"))
        }
        holder.score.text = rankList[position].score.toString()
        if(rankList[position].activePhoto == 1){
            holder.image.setBackgroundResource(R.drawable.main_pfp)
        }else if(rankList[position].activePhoto == 2){
            holder.image.setImageResource(R.drawable.monster_pfp)
        }else if(rankList[position].activePhoto == 3){
            holder.image.setBackgroundResource(R.drawable.robo_pfp)
        }else if(rankList[position].activePhoto == 4){
            holder.image.setBackgroundResource(R.drawable.disc_pfp)
        }else if(rankList[position].activePhoto == 5){
            holder.image.setBackgroundResource(R.drawable.love_pfp)
        }else if(rankList[position].activePhoto == 6){
            holder.image.setBackgroundResource(R.drawable.moon_pfp)
        }else if(rankList[position].activePhoto == 7){
            holder.image.setBackgroundResource(R.drawable.cat_pfp)
        }else if(rankList[position].activePhoto == 8){
            holder.image.setBackgroundResource(R.drawable.axe_pfp)
        }else if(rankList[position].activePhoto == 9){
            holder.image.setBackgroundResource(R.drawable.game_pfp)
        }
    }

    override fun getItemCount(): Int {
        return rankList.size
    }
}