package zuper.dev.android.dashboard.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.components.Header
import zuper.dev.android.dashboard.components.HorizontalView
import zuper.dev.android.dashboard.components.ProgressView
import zuper.dev.android.dashboard.data.model.InvoiceModel
import zuper.dev.android.dashboard.data.model.JobModel
import zuper.dev.android.dashboard.navigation.PlaceHolder
import zuper.dev.android.dashboard.navigation.Screens
import zuper.dev.android.dashboard.snippet.getRoute
import zuper.dev.android.dashboard.snippet.getTodayDate
import zuper.dev.android.dashboard.state.DashboardState
import zuper.dev.android.dashboard.ui.theme.BgGrey
import zuper.dev.android.dashboard.ui.theme.TextGrey
import zuper.dev.android.dashboard.ui.theme.ViewGrey
import zuper.dev.android.dashboard.viewModel.DashboardViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Preview(showBackground = true)
@Composable
fun DashboardPage(navController: NavHostController) {

    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val jobState = remember { mutableStateOf<JobModel?>(null) }
    val invoiceState = remember { mutableStateOf<InvoiceModel?>(null) }

    LaunchedEffect(key1 = Unit, block = {
        dashboardViewModel.dashboardFlow.collect { state ->
            when (state) {
                is DashboardState.UpdateJobs -> {
                    jobState.value = state.jobModel
                }

                is DashboardState.UpdateInvoice -> {
                    invoiceState.value = state.invoiceModel
                }
            }
        }
    })

    LaunchedEffect(key1 = Unit, block = {
        dashboardViewModel.fetchJobs()
        dashboardViewModel.fetchInvoice()
    })

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Header(navController, "Dashboard")
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
                        .padding(bottom = 12.dp)
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
                        JobSection(
                            jobState,
                            navController
                        )
                    }

                    item {
                        InvoiceSection(
                            invoiceState
                        )
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
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
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

        Text(text = stringResource(id = R.string.greetings),
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

        Text(text = getTodayDate(),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
                color = TextGrey
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(end = 16.dp, top = 6.dp)
                .constrainAs(tvDate) {
                    top.linkTo(tvGreetings.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(imgProfile.start)
                    width = Dimension.fillToConstraints
                }
        )

        constrain(
            createVerticalChain(tvGreetings, tvDate, chainStyle = ChainStyle.Packed)
        ) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    }
}

@Composable
fun JobSection(jobState: MutableState<JobModel?>, navController: NavHostController) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = ViewGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                navController.navigate(
                    Screens.JOB.getRoute(
                        URLEncoder.encode(
                            Gson().toJson(jobState.value),
                            StandardCharsets.UTF_8.name()
                        ),
                        PlaceHolder.JOB_DATA
                    )
                )
            }
            .padding(vertical = 16.dp)
    ) {
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
                .constrainAs(vHorizontal) {
                    centerHorizontallyTo(parent)
                    top.linkTo(tvLabel.bottom)
                }
        )

        Text(text = "${jobState.value?.total} Jobs",
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

        Text(text = "${jobState.value?.completed} of ${jobState.value?.total} completed",
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
                .constrainAs(progressSection) {
                    centerHorizontallyTo(parent)
                    top.linkTo(tvTotal.bottom)
                },
            jobState.value?.total,
            jobState.value?.jobMap,
            true
        )
    }
}

@Composable
fun InvoiceSection(invoiceState: MutableState<InvoiceModel?>) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = ViewGrey,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 16.dp)
    ) {
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
                .constrainAs(vHorizontal) {
                    centerHorizontallyTo(parent)
                    top.linkTo(tvLabel.bottom)
                }
        )

        Text(text = "Total value ($${invoiceState.value?.total})",
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

        Text(text = "$${invoiceState.value?.collected} collected",
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
                .constrainAs(progressSection) {
                    centerHorizontallyTo(parent)
                    top.linkTo(tvTotal.bottom)
                },
            total = invoiceState.value?.total,
            jobMap = invoiceState.value?.invoiceMap,
            false
        )

    }
}