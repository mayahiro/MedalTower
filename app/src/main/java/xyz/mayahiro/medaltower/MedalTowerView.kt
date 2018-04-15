package xyz.mayahiro.medaltower

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View

class MedalTowerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    // draw
    private val density = context.resources.displayMetrics.density
    private val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    private val paintFill = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.white)
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private val path = Path()
    private val rect = Rect()
//    private val baseTextSize: Float
//    private var fixTextSize = false
//    private var textRectBottom = 0

    // tower
    private val towers: MutableList<Tower> = mutableListOf()

    // animation
    private var animator: ObjectAnimator? = null
    private var phase = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.apply {
            paint.textSize = 12 * density
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val width = canvas.width
            val height = canvas.height

            val columnWidth = width / towers.count()

            // name
            var maxNameHeight = 0
            var maxRectBottom = 0
            towers.forEach { tower ->
                paint.getTextBounds(tower.name, 0, tower.name.length, rect)
                maxNameHeight = Math.max(maxNameHeight, rect.height())
                maxRectBottom = Math.max(maxRectBottom, rect.bottom)
            }
            towers.forEachIndexed { index, tower ->
                canvas.drawText(tower.name, columnWidth * (index + 0.5f), height.toFloat() - maxRectBottom, paint)
            }

            // medal
            paint.style = Paint.Style.STROKE
            val medalWidth = columnWidth * 0.6
            val medalHeight = 6 * density
            towers.forEachIndexed { index, tower ->
                for (i in 0..(Math.round((tower.count - 1) * phase))) {
                    paintFill.color = ContextCompat.getColor(context, android.R.color.white)
                    path.moveTo(columnWidth * index + columnWidth * 0.2f + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 1.5f))
                    path.lineTo(columnWidth * index + columnWidth * 0.2f + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 0.5f))
                    path.addArc(
                            columnWidth * index + columnWidth * 0.2f + tower.flickers[i],
                            height - maxNameHeight - 4 * density - medalHeight * (i + 1),
                            (columnWidth * index + columnWidth * 0.2f + medalWidth + tower.flickers[i]).toFloat(),
                            height - maxNameHeight - 4 * density - medalHeight * i,
                            180f,
                            -180f
                    )
                    path.moveTo((columnWidth * index + columnWidth * 0.2f + medalWidth).toFloat() + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 0.5f))
                    path.lineTo((columnWidth * index + columnWidth * 0.2f + medalWidth).toFloat() + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 1.5f))
                    canvas.drawPath(path, paintFill)
                    canvas.drawPath(path, paint)

                    path.reset()

                    path.moveTo(columnWidth * index + columnWidth * 0.2f + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 1.5f))
                    path.lineTo(columnWidth * index + columnWidth * 0.2f + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 0.5f))
                    path.lineTo((columnWidth * index + columnWidth * 0.2f + medalWidth).toFloat() + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 0.5f))
                    path.lineTo((columnWidth * index + columnWidth * 0.2f + medalWidth).toFloat() + tower.flickers[i], height - maxNameHeight - 4 * density - medalHeight * (i + 1.5f))
                    path.close()
                    canvas.drawPath(path, paintFill)

                    path.reset()

                    paintFill.color = ContextCompat.getColor(context, android.R.color.darker_gray)
                    canvas.drawOval(
                            columnWidth * index + columnWidth * 0.2f + tower.flickers[i],
                            height - maxNameHeight - 4 * density - medalHeight * (i + 2f),
                            (columnWidth * index + columnWidth * 0.2f + medalWidth + tower.flickers[i]).toFloat(),
                            height - maxNameHeight - 4 * density - medalHeight * (i + 1f),
                            paintFill
                    )
                    canvas.drawOval(
                            columnWidth * index + columnWidth * 0.2f + tower.flickers[i],
                            height - maxNameHeight - 4 * density - medalHeight * (i + 2f),
                            (columnWidth * index + columnWidth * 0.2f + medalWidth + tower.flickers[i]).toFloat(),
                            height - maxNameHeight - 4 * density - medalHeight * (i + 1f),
                            paint
                    )
                }
            }
        }
        super.onDraw(canvas)
    }

    fun startAnimation() {
        animator?.let {
            if (it.isRunning) {
                it.end()
                it.cancel()
                clearAnimation()
            }
        }

        animator = ObjectAnimator.ofFloat(this, "phase", 0f, 1f).apply {
            duration = 2000L
        }

        animator?.start()
    }

    fun setData(towers: List<Tower>) {
        this.towers.clear()
        this.towers.addAll(towers)
    }
}
