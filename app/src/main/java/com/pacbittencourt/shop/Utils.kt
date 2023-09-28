package com.pacbittencourt.shop

import java.util.Locale

fun Double.formatCurrency() = "%.2f".format(Locale.getDefault(), this)