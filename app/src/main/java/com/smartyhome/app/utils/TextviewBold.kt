package com.smartyhome.app.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.smartyhome.app.R


class TextviewBold : TextView {

    constructor(context: Context) : super(context) {
        applyCustomFont(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyCustomFont(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        applyCustomFont(context)
    }

    private fun applyCustomFont(context: Context) {
//        val customFont: Typeface = FontCache.getTypeface("SourceSansPro-Regular.ttf", context)
        var typeFace: Typeface? = ResourcesCompat.getFont(context, R.font.poppins_bold)
        setTypeface(typeFace)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)
}