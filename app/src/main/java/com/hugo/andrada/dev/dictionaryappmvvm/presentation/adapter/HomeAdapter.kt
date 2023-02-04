package com.hugo.andrada.dev.dictionaryappmvvm.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hugo.andrada.dev.dictionaryappmvvm.databinding.ItemRowLayoutBinding
import com.hugo.andrada.dev.dictionaryappmvvm.domain.model.WordInfo

class HomeAdapter :
    ListAdapter<WordInfo, HomeAdapter.HomeViewHolder>(HomeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class HomeViewHolder(
        private val binding: ItemRowLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: WordInfo) {
            binding.apply {
                txtTitle.text = word.word
                word.meanings.forEach { meaning ->
                    txtDefinition.text = meaning.partOfSpeech
                    meaning.definitions.forEach {
                        txtDefinition.text = it.definition
                    }
                }
            }
        }
    }

    class HomeDiffUtil : DiffUtil.ItemCallback<WordInfo>() {
        override fun areItemsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            return oldItem == newItem
        }
    }
}