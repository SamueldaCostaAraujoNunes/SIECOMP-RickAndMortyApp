package br.com.samuelnunes.rickandmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import br.com.samuelnunes.rickandmorty.R
import br.com.samuelnunes.rickandmorty.data.entities.Character
import br.com.samuelnunes.rickandmorty.databinding.CharactersFragmentBinding
import br.com.samuelnunes.rickandmorty.databinding.ItemCharacterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class CharactersFragment : Fragment(), CharacterItemListener {

    private lateinit var binding: CharactersFragmentBinding
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postponeEnterTransition()
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        val drawListener = object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                startPostponedEnterTransition()
                binding.charactersRv.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        }
        binding.charactersRv.viewTreeObserver.addOnPreDrawListener(drawListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter(this)
        binding.charactersRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.characters.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onClickedCharacter(character: Character, viewBinding: ItemCharacterBinding) {
        val imageView = findViewForTransition(binding.charactersRv, R.id.image, character.id)
        val textView = findViewForTransition(binding.charactersRv, R.id.name, character.id)
        val direction =
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(character.id)
        val extras = FragmentNavigatorExtras(
            imageView to imageView.transitionName,
            textView to textView.transitionName
        )
        findNavController().navigate(direction, extras)
    }

    private fun findViewForTransition(group: ViewGroup, idView: Int, id: Int): View {
        group.forEach {
            if (it.getTag(R.id.character_id_tag) == id) {
                return it.findViewById(idView)
            }
        }
        return group
    }
}
