package com.mobile.entertainme.view.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobile.entertainme.databinding.FragmentScheduleBinding
import com.mobile.entertainme.view.add.AddScheduleActivity

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scheduleViewModel =
            ViewModelProvider(this)[ScheduleViewModel::class.java]

        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabAddSchedule.setOnClickListener {
            startActivity(Intent(requireContext(), AddScheduleActivity::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}