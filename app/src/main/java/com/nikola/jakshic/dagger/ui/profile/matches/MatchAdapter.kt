package com.nikola.jakshic.dagger.ui.profile.matches

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.MeasureSpec.getMode
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.nikola.jakshic.dagger.R
import com.nikola.jakshic.dagger.inflate
import com.nikola.jakshic.dagger.model.match.Match
import com.nikola.jakshic.dagger.util.DotaUtil
import kotlinx.android.synthetic.main.item_match.view.*
import java.util.concurrent.TimeUnit

class MatchAdapter(
        val listener: (Long) -> Unit) : PagedListAdapter<Match, MatchAdapter.MatchVH>(MATCH_COMPARATOR) {

    private val options = RequestOptions().centerCrop()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchVH {
        return MatchVH(parent.inflate(R.layout.item_match))
    }

    override fun onBindViewHolder(holder: MatchVH, position: Int) {
        holder.bind(getItem(position)!!)
    }

    inner class MatchVH(view: View) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener { listener(getItem(adapterPosition)!!.matchId) }
        }

        fun bind(item: Match) {
            with(itemView) {
                Glide.with(this)
                        .load(DotaUtil.getHero(context, item.heroId))
                        .transition(withCrossFade())
                        .into(imgHero)
                tvMatchResult.text = if (isWin(item)) "Won" else "Lost"
                val resultColor = if (isWin(item))
                    ContextCompat.getColor(context, R.color.color_green)
                else
                    ContextCompat.getColor(context, R.color.color_red)
                tvMatchResult.setTextColor(resultColor)
                tvMatchSkill.text = DotaUtil.skill[item.skill.toInt(), "Unknown"]
                tvMatchMode.text = DotaUtil.mode[item.gameMode.toInt(), "Unknown"]
                tvMatchLobby.text = DotaUtil.lobby[item.lobbyType.toInt(), "Unknown"]
                tvMatchDuration.text = getDuration(context, item)
                tvMatchTimePassed.text = getTimePassed(context, item)
            }
        }

        private fun isWin(item: Match): Boolean {
            if (item.isRadiantWin && item.playerSlot <= 4) return true
            if (!item.isRadiantWin && item.playerSlot > 4) return true
            return false
        }

        private fun getDuration(context: Context, item: Match): String {
            val hours = item.duration / (60 * 60)
            val minutes = (item.duration / 60) % 60
            val seconds = item.duration % 60
            if (hours != 0L) return context.resources.getString(R.string.match_duration, hours, minutes, seconds)
            return context.resources.getString(R.string.match_duration_zero_hours, minutes, seconds)
        }

        private fun getTimePassed(context: Context, item: Match): String? {
            val endTime = TimeUnit.SECONDS.toMillis(item.startTime + item.duration)
            val timePassed = System.currentTimeMillis() - endTime

            val years = TimeUnit.MILLISECONDS.toDays(timePassed) / 365
            val months = TimeUnit.MILLISECONDS.toDays(timePassed) / 30
            val days = TimeUnit.MILLISECONDS.toDays(timePassed)
            val hours = TimeUnit.MILLISECONDS.toHours(timePassed)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timePassed)

            return when {
                years > 0 -> context.resources?.getQuantityString(R.plurals.year, years.toInt(), years)
                months > 0 -> context.resources?.getQuantityString(R.plurals.month, months.toInt(), months)
                days > 0 -> context.resources?.getQuantityString(R.plurals.day, days.toInt(), days)
                hours > 0 -> context.resources?.getQuantityString(R.plurals.hour, hours.toInt(), hours)
                else -> context.resources?.getQuantityString(R.plurals.minute, minutes.toInt(), minutes)
            }
        }
    }

    companion object {
        val MATCH_COMPARATOR = object : DiffUtil.ItemCallback<Match>() {
            override fun areItemsTheSame(oldItem: Match?, newItem: Match?): Boolean {
                return oldItem?.matchId == newItem?.matchId
            }

            override fun areContentsTheSame(oldItem: Match?, newItem: Match?): Boolean {
                return oldItem == newItem
            }
        }
    }
}