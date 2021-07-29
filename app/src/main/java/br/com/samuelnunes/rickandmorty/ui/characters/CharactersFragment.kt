package br.com.samuelnunes.rickandmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import br.com.samuelnunes.rickandmorty.data.entities.Character
import br.com.samuelnunes.rickandmorty.databinding.CharactersFragmentBinding
import br.com.samuelnunes.rickandmorty.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class CharactersFragment : Fragment(), CharacterItemListener {

    private lateinit var binding: CharactersFragmentBinding
    private val viewModel: CharactersViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.showSearchView()
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
        submitList()
        mainViewModel.query.observe(viewLifecycleOwner, { query ->
            submitList(query ?: "")
        })
    }

    private fun submitList(query: String = "") {
        viewModel.characters(query).observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onClickedCharacter(character: Character) {
        val direction =
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(character.id)
        findNavController().navigate(direction)
    }

}
