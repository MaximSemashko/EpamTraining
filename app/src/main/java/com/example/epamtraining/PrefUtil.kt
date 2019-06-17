package com.example.epamtraining

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.example.epamtraining.activities.TimerActivity

class PrefUtil {
    companion object {

        fun setTimerLength(time: String): Int {
            return time.substring(0, 2).toInt()
        }

        fun getPreviousTimerLengthSeconds(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        @SuppressLint("CommitPrefEdits")
        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context) {
            val editor = PreferenceManager
                    .getDefaultSharedPreferences(context)
                    .edit()

            editor.apply {
                putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
                apply()
            }
        }

        fun getTimerState(context: Context): TimerActivity.TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)

            return TimerActivity.TimerState.values()[ordinal]
        }

        @SuppressLint("CommitPrefEdits")
        fun setTimerState(state: TimerActivity.TimerState, context: Context) {
            val editor = PreferenceManager
                    .getDefaultSharedPreferences(context)
                    .edit()

            val ordinal = state.ordinal
            editor.apply {
                putInt(TIMER_STATE_ID, ordinal)
                apply()
            }
        }

        fun getSecondsRemaining(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        @SuppressLint("CommitPrefEdits")
        fun setSecondsRemaining(seconds: Long, context: Context) {
            val editor = PreferenceManager
                    .getDefaultSharedPreferences(context)
                    .edit()

            editor.apply {
                putLong(SECONDS_REMAINING_ID, seconds)
                apply()
            }
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.example.epamtraining.previous_timer_length_seconds"
        private const val TIMER_STATE_ID = "com.example.epamtraining.timer_state"
        private const val SECONDS_REMAINING_ID = "com.example.activities.seconds_remaining"
    }
}