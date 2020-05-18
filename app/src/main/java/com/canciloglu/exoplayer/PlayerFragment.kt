package com.canciloglu.exoplayer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class PlayerFragment : Fragment() {
    private val dashUrl = "http://rdmedia.bbc.co.uk/dash/ondemand/bbb/2/client_manifest-separate_init.mpd"
    private lateinit var simpleExoplayer: SimpleExoPlayer
    private lateinit var playerView: PlayerView
    private val bandwidthMeter by lazy { DefaultBandwidthMeter.Builder(context).build() }
    private var initialised = false
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        Log.d(TAG,"onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView")
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"onViewCreated")
        
        playerView = view.findViewById(R.id.player_view)

        Log.d(TAG,"initialised: $initialised")

        if (initialised) {
            playerView.player = simpleExoplayer
        } else {
            initializeExoplayer()
            initialised = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"onAttach")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG,"onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG,"onDestroyView")
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        releaseExoplayer()
        super.onDestroy()
    }

    private fun initializeExoplayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(requireContext()).build()
        prepareExoplayer()
        playerView.player = simpleExoplayer
        simpleExoplayer.seekTo(playbackPosition)
        simpleExoplayer.playWhenReady = true
    }

    private fun releaseExoplayer() {
        playbackPosition = simpleExoplayer.currentPosition
        simpleExoplayer.release()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("ua", bandwidthMeter)
        return DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

    private fun prepareExoplayer() {
        val uri = Uri.parse(dashUrl)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource)
    }

    companion object {
        private const val TAG = "PlayerFragment-P"
    }
}