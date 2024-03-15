package com.example.immunify.ui.vacc_library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.immunify.databinding.FragmentVaccLibBinding

class VaccLibFragment : Fragment() {

    private var _binding: FragmentVaccLibBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vaccLibViewModel =
            ViewModelProvider(this).get(VaccLibViewModel::class.java)

        _binding = FragmentVaccLibBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textVaccLib
        vaccLibViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}