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
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.ui.theme.TextGrey

@Preview(showBackground = true)
@Composable
fun StatusView(job: Map.Entry<Any?, Pair<Int, MutableList<*>?>>, isJob: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(
                    if (isJob) {
                        getJobColor(job.key as JobStatus)
                    } else {
                        getInvoiceColor(job.key as InvoiceStatus)
                    },
                    shape = RoundedCornerShape(2.dp))
        )

        Text(
            text = if (isJob) {
                "${getJobStatusName(job.key as JobStatus)} (${job.value.first})"
            } else {
                "${getInvoiceStatusName(job.key as InvoiceStatus)} ($${job.value.first})"
            },
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

fun getJobStatusName(type: JobStatus): String {
    return when (type) {
        JobStatus.YetToStart -> "Yet to start"
        JobStatus.InProgress -> "In-Progress"
        JobStatus.Canceled -> "Cancelled"
        JobStatus.Completed -> "Completed"
        JobStatus.Incomplete -> "In-Complete"
    }
}

fun getInvoiceStatusName(type: InvoiceStatus): String {
    return when (type) {
        InvoiceStatus.Draft -> "Draft"
        InvoiceStatus.Pending -> "Pending"
        InvoiceStatus.Paid -> "Paid"
        InvoiceStatus.BadDebt -> "Bad Debt"
    }
}