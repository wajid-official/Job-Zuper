package zuper.dev.android.dashboard.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.snippet.getPercent


@Composable
fun ProgressViewBar(
    modifier: Modifier = Modifier,
    jobMap: Map<*, Pair<Int, MutableList<*>?>>?,
    isJob: Boolean,
    total: Int?
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
    ) {

        jobMap?.map {
            if (it.value.first > 0) {
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .weight(
                            total?.getPercent(it.value.first) ?: 0f
                        )
                        .background(
                            if (isJob) {
                                getJobColor(it.key as JobStatus)
                            } else {
                                getInvoiceColor(it.key as InvoiceStatus)
                            }
                        )
                )
            }
        }
    }
}