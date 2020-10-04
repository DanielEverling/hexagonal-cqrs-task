package com.cross.commons.extensions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

import java.text.DecimalFormatSymbols
import java.util.Locale

private val SYMBOL = DecimalFormatSymbols(Locale("pt", "BR"))
fun BigDecimal.format(): String = DecimalFormat("#,###,##0.00", SYMBOL).format(this.toDouble())
fun BigDecimal.toSixDecimalPlaces(): BigDecimal = this.setScale(6, RoundingMode.HALF_EVEN)