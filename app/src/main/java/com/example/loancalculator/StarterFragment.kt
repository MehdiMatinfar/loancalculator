package com.example.loancalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.loancalculator.databinding.FragmentStarterBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class StarterFragment : Fragment() {


    lateinit var binding: FragmentStarterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentStarterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var animFadeIn: Animation? = AnimationUtils.loadAnimation(
            requireActivity().getApplicationContext(),
            R.anim.fade_in
        )
        binding.splash.startAnimation(animFadeIn)
        val bottomToTop = AnimationUtils.loadAnimation(requireContext(), R.anim.bottom_to_top)
        binding.textView7.startAnimation(bottomToTop)


lifecycleScope.launch {

    delay(3500L)
    findNavController().navigate(R.id.action_starterFragment_to_firstFragment)

}


    }

}