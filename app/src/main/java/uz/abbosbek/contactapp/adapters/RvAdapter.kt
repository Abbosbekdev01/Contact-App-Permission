package com.example.contactpermission.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.example.contactpermission.databinding.RvItemBinding
import com.example.contactpermission.models.ContactType
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.contactpermission.R
import uz.abbosbek.contactapp.databinding.ItemRvBinding
import uz.abbosbek.contactapp.models.ContactInfo

class RvAdapter(val context: Context, var list: List<ContactInfo>, val rvClick: RvClick) :
    RecyclerSwipeAdapter<RvAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {


        @SuppressLint("SetTextI18n")
        fun onBind(user: ContactInfo, position: Int) {

            /** default card values*/
            itemRvBinding.apply {
                tvName.text = user.name
                tvNumber.text = "+998 "+user.number
                tvCount.text = "${position + 1}"

                phoneCall.setOnClickListener {
                    rvClick.rvCallClicked(user)
                }
                itemCard.setOnClickListener {
                    rvClick.rvItemClicked(user, position)
                }
            }

            /** dealing with swipeLayout */
            itemRvBinding.apply {
                btnCall.setOnClickListener {
                    rvClick.rvCallClicked(user)
                }
                btnSms.setOnClickListener {
                    rvClick.rvSmsClicked(user)
                }
            }
        }
    }

    val  mItemManger =  SwipeItemRecyclerMangerImpl(this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {

        holder.onBind(list[position], position)
        mItemManger.bindView(holder.itemView, position);
    }

    override fun getItemCount(): Int = list.size
    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }
}

interface RvClick {
    fun rvItemClicked(user: ContactInfo, position: Int)
    fun rvCallClicked(user: ContactInfo)
    fun rvSmsClicked(user: ContactInfo)
}