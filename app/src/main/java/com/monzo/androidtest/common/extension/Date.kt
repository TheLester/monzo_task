package com.monzo.androidtest.common.extension

import android.annotation.SuppressLint
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

@SuppressLint("SimpleDateFormat")
fun Instant.asReadableString() =
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .format(LocalDateTime.ofInstant(this, ZoneId.systemDefault()))

