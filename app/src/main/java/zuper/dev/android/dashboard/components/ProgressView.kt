package zuper.dev.android.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.ui.theme.Canceled
import zuper.dev.android.dashboard.ui.theme.Completed
import zuper.dev.android.dashboard.ui.theme.InComplete
import zuper.dev.android.dashboard.ui.theme.Pending
import zuper.dev.android.dashboard.ui.theme.YetToStart


@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun ProgressView(
    modifier: Modifier = Modifier
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (progressSection, progressInfo) = createRefs()

        ProgressViewBar(
            modifier = Modifier
                .constrainAs(progressSection) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                }
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp,
                alignment = Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp,
                alignment = Alignment.CenterVertically),
            maxItemsInEachRow = 2,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .constrainAs(progressInfo) {
                    centerHorizontallyTo(parent)
                    top.linkTo(progressSection.bottom)
                }
        ) {
            (1..5).map {
                StatusView()
            }
        }

        /*LazyVerticalGrid(columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .constrainAs(progressInfo) {
                    centerHorizontallyTo(parent)
                    top.linkTo(progressSection.bottom)
                }){

            items(5){
                StatusView()
            }
        }*/


    }
}

/*@Preview(showBackground = true)
@Composable
fun ProgressView(
    dataMap: HashMap<Any, Pair<Int, MutableList<Any>>>,
    total: Int, isJob: Boolean
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (progressSection) = createRefs()

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            *//*dataMap.map {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .weight(total.getPercent(it.value.first))
                        .background(
                            if (isJob) getJobColor(it.key as JobStatus)
                            else getInvoiceColor(it.key as InvoiceStatus)
                        )
                )
            }*//*

             Box(modifier = Modifier
                 .height(40.dp)
                 .weight(0.3f)
                 .background(Color.Blue))

             Box(modifier = Modifier
                 .height(40.dp)
                 .weight(0.2f)
                 .background(Color.Yellow))

             Box(modifier = Modifier
                 .height(40.dp)
                 .weight(0.1f)
                 .background(Color.Red))
        }
    }
}*/

private fun getJobColor(type: JobStatus): Color {
    return when (type) {
        JobStatus.YetToStart -> YetToStart
        JobStatus.InProgress -> Pending
        JobStatus.Canceled -> Canceled
        JobStatus.Completed -> Completed
        JobStatus.Incomplete -> InComplete
    }
}

private fun getInvoiceColor(type: InvoiceStatus): Color {
    return when (type) {
        InvoiceStatus.Draft -> Canceled
        InvoiceStatus.Pending -> Pending
        InvoiceStatus.Paid -> Completed
        InvoiceStatus.BadDebt -> InComplete
    }
}