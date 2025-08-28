package elmeniawy.eslam.nutrisport.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import elmeniawy.eslam.nutrisport.categories.component.CategoryCard
import elmeniawy.eslam.nutrisport.shared.domain.ProductCategory

/**
 * CategoriesScreen
 *
 * Created by Eslam El-Meniawy on 28-Aug-2025 at 10:08 AM.
 */

@Composable
fun CategoriesScreen(
    navigateToCategorySearch: ((String) -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProductCategory.entries.forEach { category ->
            CategoryCard(
                category = category,
                onClick = { navigateToCategorySearch?.invoke(category.name) }
            )
        }
    }
}