package elmeniawy.eslam.nutrisport

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform