package com.abubakar.features.util.ext

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ChipGroup.addChips(tags: List<String>) {

    tags.forEach { tag ->
        val chip = Chip(this.context)
        chip.text = tag
        chip.isClickable = false
        addView(chip)
    }
}