package utils

fun Number.toCondition(): String {
    return when (this.toInt()) {
        0 -> "Clear"
        in listOf(1, 2, 3, 45, 48) -> "Cloud"
        in listOf(51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82, 95, 96, 99) -> "Rainy"
        in listOf(71, 73, 75, 77, 85, 86) -> "Snowy"
        else -> "Unknown"
    }
}

fun String.toEmoji(): String {
    return when (this) {
        "Clear" -> "☀️"
        "Rainy" -> "🌧️"
        "Cloud" -> "☁️"
        "Snowy" -> "🌨️"
        else -> "Unknown"
    }
}