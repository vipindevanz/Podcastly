package com.pns.podcastly.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class WaveFormView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paint = Paint()
    private var amplitudes = ArrayList<Float>()
    private var spikes = ArrayList<RectF>()

    private var radius = 6f
    private var w = 6f
    private var d = 6f

    private var sw = 0f
    private var sh = 400f

    private var maxSpikes = 0

    init {
        paint.color = Color.rgb(143, 18, 252)
        sw = resources.displayMetrics.widthPixels.toFloat()

        maxSpikes = (sw/(w+d)).toInt()
    }

    fun addAmplitudes(amp: Float) {
        val norm = (amp.toInt() / 7).coerceAtMost(400).toFloat()
        amplitudes.add(norm)

        spikes.clear()

        val amps = amplitudes.takeLast(maxSpikes)

        for(i in amps.indices) {

            val left = sw - i*(w+d)
            val top = sh/2 - amps[i]/2
            val right = left + w
            val bottom = top + amps[i]
            spikes.add(RectF(left, top, right, bottom))

            val r = (0..256).random()
            val g = (0..256).random()
            val b = (0..256).random()
            paint.color = Color.rgb(r, g, b)
        }
        invalidate()
    }

    fun clear() : ArrayList<Float> {

        val amps = amplitudes.clone() as ArrayList<Float>
        amplitudes.clear()
        spikes.clear()
        invalidate()

        return amps
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        spikes.forEach {
            canvas?.drawRoundRect(it, radius, radius, paint)
        }
    }
}