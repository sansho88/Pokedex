package fr.tgriffit.pokedex.main

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.tgriffit.pokedex.R
import fr.tgriffit.pokedex.data.model.Move
import fr.tgriffit.pokedex.databinding.FragmentSkillItemBinding

/**
 * [RecyclerView.Adapter] that can display a [SkillsFragment].
 */
class SkillsViewAdapter(
    private val skillsList: List<Move>?
) : RecyclerView.Adapter<SkillsViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentSkillItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = skillsList!![position]
        holder.skillNameView.text = item.move.name
            .replace('-', ' ')
            .replaceFirstChar { it.uppercase() }
        val learnedAtLevel = item.version_group_details[0].level_learned_at


        holder.learnLvlView.text = "Lvl $learnedAtLevel"
        val scoreColor = when {
            learnedAtLevel <= 20 -> R.color.light_green
            learnedAtLevel >= 50 -> R.color.red
            else -> R.color.orange
        }
        val backgroundColor = when(scoreColor){
            R.color.light_green -> R.color.trans_green
            R.color.red -> R.color.trans_red
            else -> R.color.trans_orange
        }
        holder.learnLvlView.setTextColor(holder.itemView.context.getColor(scoreColor))
        holder.itemView.context.getColor(scoreColor)
        holder.itemView.backgroundTintList = ColorStateList.valueOf(holder.itemView.context.getColor(backgroundColor))

    }

    override fun getItemCount(): Int = skillsList?.size ?: 0

    inner class ViewHolder(binding: FragmentSkillItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val skillNameView: TextView = binding.skillNameTxt
        val learnLvlView: TextView = binding.learnLvlTxt

    }

}