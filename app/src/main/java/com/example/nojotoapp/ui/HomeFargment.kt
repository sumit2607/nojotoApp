package com.example.nojotoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.nojotoapp.Adapters.ViewPagerVideoAdapter
import com.example.nojotoapp.R
import com.example.nojotoapp.api.RetrofitService.retrofitService
import com.example.nojotoapp.databinding.ActivityMainBinding
import com.strangecoder.videostreaming.network.model.Video
import kotlinx.android.synthetic.main.fragment_home_fargment.*
import kotlinx.coroutines.launch


class HomeFargment : Fragment() {

    private val videoList = MutableLiveData<List<Video>>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_fargment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val result = retrofitService.getPopularVideosAsync().await()
            videoList.value = result
        }
        videoList.observe(viewLifecycleOwner) {
            videoViewPager.adapter = ViewPagerVideoAdapter(it)
        }
    }




}