package zuper.dev.android.dashboard.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.components.Header
import zuper.dev.android.dashboard.components.HorizontalView
import zuper.dev.android.dashboard.components.ProgressView
import zuper.dev.android.dashboard.ui.theme.BgGrey
import zuper.dev.android.dashboard.ui.theme.TextGrey
import zuper.dev.android.dashboard.ui.theme.ViewGrey

@Preview(showBackground = true)
@Composable
fun DashboardPage() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Header()
        },
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(BgGrey)
            ) {

                val (headingLine, contentLayout) = createRefs()

                HorizontalView(
                    modifier = Modifier
                        .constrainAs(headingLine) {
                            centerHorizontallyTo(parent)
                            top.linkTo(parent.top)
                        },
                    2
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                        .constrainAs(contentLayout) {
                            centerHorizontallyTo(parent)
                            top.linkTo(headingLine.bottom)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                        }
                ) {

                    item {
                        ProfileSection()
                    }

                    item {
                        JobSection()
                    }

                    item {
                        InvoiceSection()
                    }
                }
            }
        }
    )
}

@Composable
fun ProfileSection() {
    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = ViewGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        val (imgProfile, tvGreetings, tvDate) = createRefs()

        Image(painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "",
            modifier = Modifier
                .size(68.dp)
                .border(
                    width = 2.dp,
                    color = ViewGrey,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .constrainAs(imgProfile) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end)
                })

        Text(text = "Hello, Henry sdfs d fds fsd sfd sd ssdf dfsd ",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(end = 16.dp)
                .constrainAs(tvGreetings) {
                    start.linkTo(parent.start)
                    end.linkTo(imgProfile.start)
                    width = Dimension.fillToConstraints
                }
        )

        Text(text = "Hello, Henry sdfs d fds fsd sfd sd ssdf dfsd ",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
                color = TextGrey
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(end = 16.dp, top = 8.dp)
                .constrainAs(tvDate) {
                    top.linkTo(tvGreetings.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(imgProfile.start)
                    width = Dimension.fillToConstraints
                }
        )

        constrain(
            createVerticalChain(tvGreetings, tvDate, chainStyle = ChainStyle.Packed)
        ){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    }
}

@Composable
fun JobSection(){
    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = ViewGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 16.dp)
    ){
        val (tvLabel, vHorizontal, tvTotal, tvStat, progressSection) = createRefs()

        Text(text = stringResource(id = R.string.label_job_stats),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
            ),
            modifier = Modifier
                .constrainAs(tvLabel) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp)
        )

        HorizontalView(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .constrainAs(vHorizontal){
                    centerHorizontallyTo(parent)
                    top.linkTo(tvLabel.bottom)
                }
        )

        Text(text = "60 Jobs",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextGrey
            ),
            modifier = Modifier
                .constrainAs(tvTotal) {
                    top.linkTo(vHorizontal.bottom)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp)
        )

        Text(text = "25 of 60 completed",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextGrey
            ),
            modifier = Modifier
                .constrainAs(tvStat) {
                    top.linkTo(vHorizontal.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 16.dp)
        )

        ProgressView(
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                .constrainAs(progressSection){
                    centerHorizontallyTo(parent)
                    top.linkTo(tvTotal.bottom)
                }
        )

    }
}

@Composable
fun InvoiceSection(){
    ConstraintLayout(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = ViewGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 16.dp)
    ){
        val (tvLabel, vHorizontal, tvTotal, tvStat, progressSection) = createRefs()

        Text(text = stringResource(id = R.string.label_invoice_stats),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
            ),
            modifier = Modifier
                .constrainAs(tvLabel) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp)
        )

        HorizontalView(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .constrainAs(vHorizontal){
                    centerHorizontallyTo(parent)
                    top.linkTo(tvLabel.bottom)
                }
        )

        Text(text = "60 Jobs",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextGrey
            ),
            modifier = Modifier
                .constrainAs(tvTotal) {
                    top.linkTo(vHorizontal.bottom)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp)
        )

        Text(text = "25 of 60 completed",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextGrey
            ),
            modifier = Modifier
                .constrainAs(tvStat) {
                    top.linkTo(vHorizontal.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 16.dp)
        )

        ProgressView(
            modifier = Modifier
                .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                .constrainAs(progressSection){
                    centerHorizontallyTo(parent)
                    top.linkTo(tvTotal.bottom)
                }
        )

    }
}