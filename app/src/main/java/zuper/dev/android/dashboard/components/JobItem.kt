package zuper.dev.android.dashboard.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.snippet.convertDate
import zuper.dev.android.dashboard.snippet.isSameDayAs
import zuper.dev.android.dashboard.snippet.isToday
import zuper.dev.android.dashboard.ui.theme.TextGrey
import zuper.dev.android.dashboard.ui.theme.ViewGrey

@Preview(showBackground = true)
@Composable
fun JobItem(jobApiModel: JobApiModel) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .background(Color.White)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = ViewGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        val (sNo, name, time) = createRefs()

        Text(text = "#${jobApiModel.jobNumber}",
            style = TextStyle(
                fontSize = 14.sp,
                color = TextGrey,
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier
                .constrainAs(sNo) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )

        Text(text = jobApiModel.title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            ),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
                .constrainAs(name) {
                    centerHorizontallyTo(parent)
                    top.linkTo(sNo.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        Text(text = getJobDisplayTime(jobApiModel.startTime, jobApiModel.endTime),
            style = TextStyle(
                fontSize = 14.sp,
                color = TextGrey,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 6.dp)
                .constrainAs(time) {
                    centerHorizontallyTo(parent)
                    top.linkTo(name.bottom)
                    width = Dimension.fillToConstraints
                }
        )

    }
}

private fun getJobDisplayTime(
    startTime: String,
    endTime: String
): String {
    if (startTime.isSameDayAs(endTime)) {
        return "${
            if (startTime.isToday())
                "Today"
            else
                startTime.convertDate(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "dd/MM/yyyy"
                )
        }, ${
            startTime.convertDate(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "hh:mm"
            )
        } - ${
            endTime.convertDate(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "hh:mm a"
            ).uppercase()
        }"
    } else {
        return "${
            startTime.convertDate(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "dd/MM/yyyy, hh:mm a"
            ).uppercase()
        } -> ${
            endTime.convertDate(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "dd/MM/yyyy, hh:mm a"
            ).uppercase()
        }"
    }
}
