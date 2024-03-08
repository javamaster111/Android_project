package com.example.diplomaproject

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.databinding.FragmentAnimation1Binding
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class AnimationFragment: Fragment() {

    private lateinit var binding: FragmentAnimation1Binding
    val twoSeconds: Duration = 2000.milliseconds

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimation1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Run.after(1000) {
            binding.anim1.isVisible = false
            Run.after(300) {
                binding.anim1.isVisible = true
            }
        }

        binding.circle1.animate().translationY(550f)
        Run.after(1000) {
            binding.circle1.animate().translationX(300f).translationY(-100f).duration = 800

            Run.after(500) {
                binding.circle1.scaleX = 5f
                binding.circle1.scaleY = 5f
            }
        }
        binding.layoutt.setOnClickListener{
            findNavController().navigate(R.id.action_animationFragment_to_startFragment)
        }
    }
}
class Run {
    companion object {
        fun after(delay: Long, process: () -> Unit) {
            Handler().postDelayed({
                process()
            }, delay)
        }
    }
}