package com.example.kinopoisk

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner

fun Fragment.composeView(
    context: Context = requireContext(),
    apply: ComposeView.() -> Unit,
): View = context.composeView(apply, this)

fun Context.composeView(
    apply: ComposeView.() -> Unit,
    lifecycleOwner: LifecycleOwner,
    height: Int = ViewGroup.LayoutParams.MATCH_PARENT
): View =
    ComposeView(this).apply {
        rootView?.setViewTreeLifecycleOwner(lifecycleOwner)

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            height
        )
    }.apply(apply)
