package zuper.dev.android.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zuper.dev.android.dashboard.ui.theme.TextGrey

@Preview(showBackground = true)
@Composable
fun StatusView() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(Color.Red, shape = RoundedCornerShape(2.dp))
        )

        Text(text = "Yet to start",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextGrey
            ),
            modifier = Modifier
                .padding(start = 8.dp)
        )

    }
}