package pl.training.goodweather.commons.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import pl.training.goodweather.R

class ImageBox(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private val image: ImageView
    private val label: TextView

    init {
        val settings = context.obtainStyledAttributes(attributeSet, R.styleable.ImageBox)
        inflate(context, R.layout.view_image_box, this)
        image = findViewById(R.id.pictureImageView)
        label = findViewById(R.id.labelTextView)
        image.setImageDrawable(settings.getDrawable(R.styleable.ImageBox_image))
        label.text = settings.getString(R.styleable.ImageBox_label)
        settings.recycle()
    }

    fun setOnClickListener(handler: () -> Unit) {
        image.setOnClickListener { handler() }
    }

}