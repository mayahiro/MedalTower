package xyz.mayahiro.medaltower

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val random = Random()
        val density = resources.displayMetrics.density

        val medalTowerView = findViewById<MedalTowerView>(R.id.medal_tower_view)
        medalTowerView.setData(mutableListOf(
                Tower("1", 10, List(10, { index ->
                    return@List when {
                        index == 0 -> 0f
                        random.nextBoolean() -> random.nextInt(4) * density
                        else -> -1 * random.nextInt(4) * density
                    }
                })),
                Tower("5", 20, List(20, { index ->
                    return@List when {
                        index == 0 -> 0f
                        random.nextBoolean() -> random.nextInt(4) * density
                        else -> -1 * random.nextInt(4) * density
                    }
                })),
                Tower("10", 3, List(3, { index ->
                    return@List when {
                        index == 0 -> 0f
                        random.nextBoolean() -> random.nextInt(4) * density
                        else -> -1 * random.nextInt(4) * density
                    }
                })),
                Tower("50", 5, List(5, { index ->
                    return@List when {
                        index == 0 -> 0f
                        random.nextBoolean() -> random.nextInt(4) * density
                        else -> -1 * random.nextInt(4) * density
                    }
                })),
                Tower("100", 1, List(1, { index ->
                    return@List when {
                        index == 0 -> 0f
                        random.nextBoolean() -> random.nextInt(4) * density
                        else -> -1 * random.nextInt(4) * density
                    }
                })),
                Tower("500", 100, List(100, { index ->
                    return@List when {
                        index == 0 -> 0f
                        random.nextBoolean() -> random.nextInt(4) * density
                        else -> -1 * random.nextInt(4) * density
                    }
                }))
        ))

        medalTowerView.startAnimation()

        medalTowerView.setOnClickListener { medalTowerView.startAnimation() }
    }
}
