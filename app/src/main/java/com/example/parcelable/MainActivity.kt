package com.example.parcelable

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.versionedparcelable.VersionedParcelize
import com.example.parcelable.databinding.ActivityMainBinding
import kotlinx.parcelize.Parcelize
import java.lang.Thread.State
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.bRandom.setOnClickListener { }
        binding.bIncrement.setOnClickListener { }
        binding.bSwitch.setOnClickListener { }

        state = savedInstanceState?.getParcelable(KEY_STATE) ?: State(
            counterValue = 0,
            counterTextColor = ContextCompat.getColor(this, R.color.teal_200),
            counterIsVisible = true
        )
        renderState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_STATE, state)
    }

    private fun increment() {
        state.counterValue++
        renderState()
    }

    private fun setRandomColor() {
        state.counterTextColor = Color.rgb(
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
        renderState()
    }

    private fun switchVisibility() {
        state.counterIsVisible = !state.counterIsVisible
        renderState()
    }

        private fun renderState() = with(binding) {
            textView.setText(state.counterValue.toString())
            textView.setTextColor(state.counterTextColor)
            textView.visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
        }

        @Parcelize
        class State(
            var counterValue: Int,
            var counterTextColor: Int,
            var counterIsVisible: Boolean
        ) : Parcelable

        companion object {
            @JvmStatic
            private val KEY_STATE = "STATE"
        }
}

