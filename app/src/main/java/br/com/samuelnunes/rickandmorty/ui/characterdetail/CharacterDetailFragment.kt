package br.com.samuelnunes.rickandmorty.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import br.com.samuelnunes.rickandmorty.databinding.CharacterDetailFragmentBinding
import br.com.samuelnunes.rickandmorty.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class CharacterDetailFragment : Fragment() {

    private val arguments by navArgs<CharacterDetailFragmentArgs>()
    private lateinit var binding: CharacterDetailFragmentBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        mainViewModel.hideSearchView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CharacterDetailFragmentBinding.inflate(inflater, container, false).also {
            binding = it
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            binding.character = viewModel.character(arguments.characterId)
        }
    }
}
