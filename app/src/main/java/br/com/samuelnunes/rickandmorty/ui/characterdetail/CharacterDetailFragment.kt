package br.com.samuelnunes.rickandmorty.ui.characterdetail

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import br.com.samuelnunes.rickandmorty.databinding.CharacterDetailFragmentBinding
import br.com.samuelnunes.rickandmorty.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
@ExperimentalPagingApi
class CharacterDetailFragment : Fragment() {

    private val arguments by navArgs<CharacterDetailFragmentArgs>()
    private lateinit var binding: CharacterDetailFragmentBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        sharedElementEnterTransition = transitionSet()
        sharedElementReturnTransition = transitionSet()
    }

    private fun transitionSet() = TransitionSet()
        .addTransition(ChangeBounds())
        .addTransition(ChangeTransform())
        .addTransition(ChangeClipBounds())
        .setOrdering(TransitionSet.ORDERING_TOGETHER)
        .setInterpolator(FastOutSlowInInterpolator())

    override fun onResume() {
        super.onResume()
        mainViewModel.hideSearchView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        binding.idTransition = arguments.characterId
        binding.lifecycleOwner = this
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        CoroutineScope(Main).launch {
            binding.character = viewModel.character(arguments.characterId)
        }
    }
}
