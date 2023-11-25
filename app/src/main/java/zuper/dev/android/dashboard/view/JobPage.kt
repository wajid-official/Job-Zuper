package zuper.dev.android.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import zuper.dev.android.dashboard.components.Header
import zuper.dev.android.dashboard.components.HorizontalView
import zuper.dev.android.dashboard.components.JobItem
import zuper.dev.android.dashboard.components.ProgressView
import zuper.dev.android.dashboard.components.ProgressViewBar
import zuper.dev.android.dashboard.ui.theme.BgGrey
import zuper.dev.android.dashboard.ui.theme.TextGrey

@Preview(showBackground = true)
@Composable
fun JobPage(){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Header()
        },
    ){
        ConstraintLayout(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(BgGrey)
        ) {
            val (headingLine, tvTotal, tvStat, progress,
                vTop, vBottom, tab, list) = createRefs()

            HorizontalView(
                modifier = Modifier
                    .constrainAs(headingLine) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    },
                2
            )

            Text(text = "60 Jobs",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextGrey
                ),
                modifier = Modifier
                    .constrainAs(tvTotal) {
                        top.linkTo(headingLine.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 16.dp, top = 24.dp)
            )

            Text(text = "25 of 60 completed",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextGrey
                ),
                modifier = Modifier
                    .constrainAs(tvStat) {
                        top.linkTo(headingLine.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(end = 16.dp, top = 24.dp)
            )

            ProgressViewBar(
                modifier = Modifier
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(progress) {
                        centerHorizontallyTo(parent)
                        top.linkTo(tvTotal.bottom)
                    }
            )

            HorizontalView(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .constrainAs(vTop) {
                        centerHorizontallyTo(parent)
                        top.linkTo(progress.bottom)
                    }
            )

            ScrollableTabRow(selectedTabIndex = 0,
                edgePadding = 0.dp,
                divider = {},
                containerColor = Color.White,
                modifier = Modifier
                    .constrainAs(tab){
                        start.linkTo(parent.start)
                        top.linkTo(vTop.bottom)
                    }) {
                (0..5).map {
                    Tab(selected = it == 0, onClick = { /*TODO*/ }) {
                        Text(text = "Yet to Start",
                            modifier = Modifier
                                .padding(16.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                color = if (it == 0) Color.Black else TextGrey
                            )
                        )
                    }
                }
            }

            HorizontalView(
                modifier = Modifier
                    .constrainAs(vBottom) {
                        centerHorizontallyTo(parent)
                        top.linkTo(tab.bottom)
                    }
            )

            LazyColumn(
                modifier = Modifier
                    .constrainAs(list){
                        centerHorizontallyTo(parent)
                        top.linkTo(vBottom.bottom)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }
            ){
                items(5){
                    JobItem()
                }
            }
        }
    }
}