package elmeniawy.eslam.nutrisport.shared.domain

import androidx.compose.ui.graphics.Color
import elmeniawy.eslam.nutrisport.shared.CategoryBlue
import elmeniawy.eslam.nutrisport.shared.CategoryGreen
import elmeniawy.eslam.nutrisport.shared.CategoryPurple
import elmeniawy.eslam.nutrisport.shared.CategoryRed
import elmeniawy.eslam.nutrisport.shared.CategoryYellow
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * Product
 *
 * Created by Eslam El-Meniawy on 14-Aug-2025 at 10:10 AM.
 */
@Serializable
data class Product @OptIn(ExperimentalTime::class) constructor(
    val id: String? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val title: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val category: String? = null,
    val flavors: List<String>? = null,
    val weight: Int? = null,
    val price: Double? = null,
    val isPopular: Boolean? = null,
    val isDiscounted: Boolean? = null,
    val isNew: Boolean? = null
)

enum class ProductCategory(
    val title: String,
    val color: Color
) {
    Protein(
        title = "Protein",
        color = CategoryYellow
    ),
    Creatine(
        title = "Creatine",
        color = CategoryBlue
    ),
    PreWorkout(
        title = "Pre-Workout",
        color = CategoryGreen
    ),
    Gainers(
        title = "Gainers",
        color = CategoryPurple
    ),
    Accessories(
        title = "Accessories",
        color = CategoryRed
    )
}