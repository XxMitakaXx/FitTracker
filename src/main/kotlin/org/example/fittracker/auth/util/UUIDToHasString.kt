package org.example.fittracker.auth.util

import java.util.UUID

fun UUID.toHexString(): String {
    val msb = this.mostSignificantBits
    val lsb = this.leastSignificantBits

    return "%016x%016x".format(msb, lsb)
}