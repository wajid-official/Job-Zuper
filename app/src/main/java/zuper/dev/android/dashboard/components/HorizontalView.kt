package zuper.dev.android.dashboard.components

import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.ui.theme.ViewGrey

@Composable
@Preview(showBackground = true)
fun HorizontalView(modifier: Modifier = Modifier, thickness: Int = 1){
    Divider(
        modifier = modifier,
        thickness = thickness.dp,
        color = ViewGrey
    )
}