package com.example.RoomRepositoryVModelSetup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.RoomRepositoryVModelSetup.R
import com.example.RoomRepositoryVModelSetup.model.Datum
import java.util.*

class UserDataAdapter(
    act: Context,
    private var listener: ((userId: Int) -> Unit)?
): RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {
      var userDataList = ArrayList<Datum> ()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.layout_user_list_item, parent, false))
    }

    override fun getItemCount(): Int {
       return userDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ViewHolder){
            val userData = userDataList?.get(position)

            val userName = userData?.firstName
            if(userName!=null){
                holder.tvUserName?.text = userName
            }else{
                holder.tvUserName?.text = "userName"
            }

            holder.tvUserId?.text = userData.id.toString()
            holder.tvUserLastName?.text = userData.lastName.toString()


            var userId = userData?.id
            holder.layoutRoot?.setOnClickListener({
                listener?.invoke(userId!!)
            })
        }
    }


        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var tvUserName : TextView ?=null
            var layoutRoot: LinearLayout ?=null
            var tvUserId: TextView ?=null
            var tvUserLastName: TextView ?=null

            init {
                tvUserName = itemView.findViewById(R.id.tv_user_name)
                tvUserId = itemView.findViewById(R.id.tv_user_id)
                tvUserLastName = itemView.findViewById(R.id.tv_user_last_name)
                layoutRoot = itemView.findViewById(R.id.layout_root)
            }
        }


    fun add(userResponseList: List<Datum?>?){
        if (userResponseList != null) {
            for(obj in userResponseList) {
                if (obj != null) {
                    addItem(obj)
                }
            }
        }
    }

    private fun addItem(obj: Datum) {
            userDataList.add(obj)
            notifyItemInserted(userDataList.size-1)
    }

}