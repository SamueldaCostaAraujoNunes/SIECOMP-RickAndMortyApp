package br.com.samuelnunes.rickandmorty.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.samuelnunes.rickandmorty.R
import br.com.samuelnunes.rickandmorty.data.entities.Character
import br.com.samuelnunes.rickandmorty.databinding.ItemCharacterBinding


class CharactersAdapter(private val listener: CharacterItemListener) :
    PagingDataAdapter<Character, CharactersViewHolder>(CharactersAdapter) {

    private companion object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding: ItemCharacterBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}

interface CharacterItemListener {
    fun onClickedCharacter(character: Character, viewBinding: ItemCharacterBinding)
}

class CharactersViewHolder(
    private val itemBinding: ItemCharacterBinding,
    private val listener: CharacterItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    private lateinit var character: Character

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Character) {
        character = item
        itemBinding.character = item
        itemBinding.root.setTag(R.id.character_id_tag, item.id)
    }

    override fun onClick(v: View?) = listener.onClickedCharacter(character, itemBinding)

}