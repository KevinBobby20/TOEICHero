package com.toeichero.project.View.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.toeichero.project.Model.badgeData
import com.toeichero.project.R
import com.skydoves.balloon.*
import kotlinx.android.synthetic.main.badge_list.view.*

class BadgeAdapter (private var badgeList: List<badgeData>, private var context: Context): RecyclerView.Adapter<BadgeAdapter.ViewHolder>() {
    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val image: ImageView = item.badge
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.badge_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(badgeList[position].image)
        holder.image.setOnClickListener {
            val balloon = createBalloon(context){
                setArrowSize(5)
                setWidthRatio(0.5f)
                setHeight(50)
                setArrowPosition(0.5f)
                setCornerRadius(4F)
                setAlpha(0.9f)
                setTextColorResource(R.color.white)
                setBackgroundColorResource(R.color.secondary)
                setBalloonAnimation(BalloonAnimation.ELASTIC)
                setText(badgeList[position].explanation)
                setLifecycleOwner(lifecycleOwner)
            }
            holder.image.showAlignTop(balloon)
            balloon.dismissWithDelay(2000)
        }

    }

    override fun getItemCount(): Int {
        return badgeList.size
    }
}