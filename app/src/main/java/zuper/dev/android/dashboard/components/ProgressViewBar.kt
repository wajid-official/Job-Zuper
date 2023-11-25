package zuper.dev.android.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ProgressViewBar(modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier
            .height(28.dp)
            .weight(0.3f)
            .background(Color.Blue))

        Box(modifier = Modifier
            .height(28.dp)
            .weight(0.2f)
            .background(Color.Yellow))

        Box(modifier = Modifier
            .height(28.dp)
            .weight(0.1f)
            .background(Color.Red))
    }
}