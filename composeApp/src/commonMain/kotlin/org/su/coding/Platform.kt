package org.su.coding

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform