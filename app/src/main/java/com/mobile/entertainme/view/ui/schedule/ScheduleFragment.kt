package com.mobile.entertainme.view.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mobile.entertainme.adapter.ScheduleAdapter
import com.mobile.entertainme.adapter.ScheduleCompletedAdapter
import com.mobile.entertainme.data.ScheduleActivity
import com.mobile.entertainme.databinding.FragmentScheduleBinding
import com.mobile.entertainme.view.add.AddScheduleActivity

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var ongoingAdapter: ScheduleAdapter
    private lateinit var completedAdapter: ScheduleCompletedAdapter
    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scheduleViewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]

        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fabAddSchedule.setOnClickListener {
            startActivity(Intent(requireContext(), AddScheduleActivity::class.java))
        }

        ongoingAdapter = ScheduleAdapter { schedule ->
            scheduleViewModel.markScheduleAsCompleted(schedule)
        }
        binding.scheduleOngoingRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.scheduleOngoingRv.adapter = ongoingAdapter


        completedAdapter = ScheduleCompletedAdapter { schedule ->
            scheduleViewModel.deleteSchedule(schedule)
        }
        binding.scheduleCompletedRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.scheduleCompletedRv.adapter = completedAdapter

        val userUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        binding.movieCategory.setOnClickListener {
            scheduleViewModel.fetchSchedules(userUid, "Movie")
        }

        binding.bookCategory.setOnClickListener {
            scheduleViewModel.fetchSchedules(userUid, "Book")
        }

        binding.travelCategory.setOnClickListener {
            scheduleViewModel.fetchSchedules(userUid, "Travel")
        }

        binding.allCategory.setOnClickListener {
            scheduleViewModel.fetchSchedules(userUid)
        }

        scheduleViewModel.schedules.observe(viewLifecycleOwner) { schedules ->
            val ongoingSchedules = schedules.filter { !it.completed }
            val completedSchedules = schedules.filter { it.completed }
            Log.d("ScheduleFragment", "Ongoing Schedules: $ongoingSchedules")
            Log.d("ScheduleFragment", "Completed Schedules: $completedSchedules")
            ongoingAdapter.submitList(ongoingSchedules)
            completedAdapter.submitList(completedSchedules)
        }

        scheduleViewModel.fetchSchedules(userUid)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}