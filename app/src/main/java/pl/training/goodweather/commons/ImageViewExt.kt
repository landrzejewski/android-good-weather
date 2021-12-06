package pl.training.goodweather.commons

import android.widget.ImageView
import androidx.core.content.ContextCompat

fun ImageView.setDrawable(name: String) {
    val id = resources.getIdentifier(name, "drawable", context.packageName)
    setImageDrawable(ContextCompat.getDrawable(context, id))
}