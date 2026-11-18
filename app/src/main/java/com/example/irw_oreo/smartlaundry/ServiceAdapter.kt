package com.example.irw_oreo.smartlaundry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.irw_oreo.databinding.ItemHomeGridBinding

class ServiceAdapter(
    private val serviceList: List<ServiceModel>,
    private val onItemClick: (ServiceModel) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(val binding: ItemHomeGridBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemHomeGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val item = serviceList[position]
        with(holder.binding) {
            tvServiceTitle.text = item.title
            tvServiceCategory.text = item.category
            tvServicePrice.text = item.price
            ivServiceIcon.setImageResource(item.iconResId)

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = serviceList.size
}