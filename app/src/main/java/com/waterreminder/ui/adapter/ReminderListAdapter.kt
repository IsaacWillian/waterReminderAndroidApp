package com.waterreminder.ui.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waterreminder.R
import com.waterreminder.models.ReminderForRecycle
import com.waterreminder.databinding.ReminderItemBinding


class ReminderListAdapter(val context: Context, val deleteReminderListener: (ReminderForRecycle) -> Unit, val onClickReminderListener: (ReminderForRecycle) -> Unit  ):
    ListAdapter<ReminderForRecycle, ReminderListAdapter.ReminderViewHolder>(ReminderComparator()) {

    class ReminderViewHolder(val itemBinding: ReminderItemBinding, val context: Context, val deleteReminderListener: (ReminderForRecycle) -> Unit, val onClickReminderListener: (ReminderForRecycle) -> Unit):RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(reminderForRecycle: ReminderForRecycle) {
            itemBinding.time.text = "${reminderForRecycle.reminder.hourToString()}:${reminderForRecycle.reminder.minutesToString()}"
            itemBinding.trashButton.setOnClickListener{
                deleteReminderListener(reminderForRecycle)
            }
            itemBinding.imageCheck.setOnClickListener{
                onClickReminderListener(reminderForRecycle)
            }
            if (reminderForRecycle.marked){
                itemBinding.imageCheck.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.checkbox_checked
                )
            )
            } else {
                val value = TypedValue()
                itemBinding.imageCheck.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.checkbox_unchecked
                ))
            }

        }
    }

    class ReminderComparator : DiffUtil.ItemCallback<ReminderForRecycle>(){
        override fun areContentsTheSame(oldItem: ReminderForRecycle, newItem: ReminderForRecycle): Boolean {
            return oldItem.reminder.hour == newItem.reminder.hour && oldItem.reminder.minutes == newItem.reminder.minutes && newItem.marked == oldItem.marked
        }

        override fun areItemsTheSame(oldItem: ReminderForRecycle, newItem: ReminderForRecycle): Boolean {
            return oldItem.reminder.id == newItem.reminder.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val itemBinding = ReminderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReminderViewHolder(itemBinding,context,deleteReminderListener,onClickReminderListener)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = getItem(position)
        holder.bind(reminder)
    }

    override fun getItemCount() = currentList.size


}