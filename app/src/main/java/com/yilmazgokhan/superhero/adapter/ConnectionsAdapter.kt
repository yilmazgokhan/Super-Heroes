package com.yilmazgokhan.superhero.adapter

import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.yilmazgokhan.superhero.R

/**
 * RecyclerView adapter via fast-adapter library
 * The adapter uses [item_text.xml]
 */
class ConnectionsAdapter : AbstractItem<ConnectionsAdapter.ViewHolder>() {

    //region vars
    var value: String? = null
    //endregion

    override val layoutRes: Int
        get() = R.layout.item_text
    override val type: Int
        get() = R.id.fastadapter_item_adapter

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<ConnectionsAdapter>(view) {

        var tvItem: TextView = view.findViewById(R.id.tvItem)

        override fun bindView(item: ConnectionsAdapter, payloads: List<Any>) {
            tvItem.text = item.value
        }

        override fun unbindView(item: ConnectionsAdapter) {
            tvItem.text = null
        }
    }
}