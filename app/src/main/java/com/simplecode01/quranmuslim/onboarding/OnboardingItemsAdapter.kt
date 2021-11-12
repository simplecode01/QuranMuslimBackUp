package com.simplecode01.quranmuslim.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.simplecode01.quranmuslim.R

class OnboardingItemsAdapter(private val onboardingItems: List<OnboardingItem>):
    RecyclerView.Adapter<OnboardingItemsAdapter.OnboardingItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        return OnboardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    inner class OnboardingItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val imageOnboarding = view.findViewById<ImageView>(R.id.imageOnBoarding)
        private val textTitles = view.findViewById<TextView>(R.id.textTitle)
        private val textDescriptions = view.findViewById<TextView>(R.id.textDescripstion)

        fun bind(onboardingItem: OnboardingItem){
            imageOnboarding.setImageResource(onboardingItem.onboardingImage)
            textTitles.text = onboardingItem.title
            textDescriptions.text = onboardingItem.descripstion
        }

    }
}