package zuper.dev.android.dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import zuper.dev.android.dashboard.components.Header
import zuper.dev.android.dashboard.components.HorizontalView
import zuper.dev.android.dashboard.components.JobItem
import zuper.dev.android.dashboard.components.ProgressViewBar
import zuper.dev.android.dashboard.components.getJobStatusName
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobModel
import zuper.dev.android.dashboard.state.JobState
import zuper.dev.android.dashboard.ui.theme.BgGrey
import zuper.dev.android.dashboard.ui.theme.TextGrey
import zuper.dev.android.dashboard.viewModel.JobViewModel

@Preview(showBackground = true)
@Composable
fun JobPage(navController: NavHostController, job: JobModel) {

    val jobViewModel: JobViewModel = hiltViewModel()
    val jobState = remember { mutableStateOf<JobModel?>(null) }
    val selectedTab = remember { mutableIntStateOf(0) }
    val selectedList = remember {
        mutableStateOf(mutableListOf<JobApiModel>())
    }
    val isLoading = jobViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading.value)

    LaunchedEffect(key1 = Unit, block = {
        jobViewModel.jobFlow.collect { state ->
            when (state) {
                is JobState.UpdateJobs -> {
                    jobState.value = state.jobModel
                    selectedTab.intValue = 0
                    jobState.value?.jobMap?.values?.first()?.second?.let {
                        selectedList.value = it
                    }
                }
            }
        }
    })

    LaunchedEffect(key1 = Unit, block = {
        jobViewModel.updateJobs(job)
    })


    SwipeRefresh(state = swipeRefreshState, onRefresh = jobViewModel::fetchJobs) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                Header(navController, "Jobs (${jobState.value?.total})")
            },
        ) {
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

                Text(text = "${jobState.value?.total} Jobs",
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

                Text(text = "${jobState.value?.completed} of ${jobState.value?.total} completed",
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
                        },
                    jobMap = jobState.value?.jobMap,
                    isJob = true,
                    total = jobState.value?.total
                )

                HorizontalView(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .constrainAs(vTop) {
                            centerHorizontallyTo(parent)
                            top.linkTo(progress.bottom)
                        }
                )

                if (jobState.value != null) {
                    ScrollableTabRow(selectedTabIndex = selectedTab.value,
                        edgePadding = 0.dp,
                        divider = {},
                        containerColor = Color.White,
                        modifier = Modifier
                            .constrainAs(tab) {
                                start.linkTo(parent.start)
                                top.linkTo(vTop.bottom)
                            }) {
                        jobState.value?.jobMap?.entries?.mapIndexed { index, entry ->
                            Tab(selected = selectedTab.value == index, onClick = {
                                selectedTab.value = index
                                entry.value.second?.let {
                                    selectedList.value = it
                                }
                            }) {
                                Text(
                                    text = getJobStatusName(entry.key),
                                    modifier = Modifier
                                        .padding(16.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp,
                                        color = if (index == selectedTab.value) Color.Black else TextGrey
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
                            .constrainAs(list) {
                                centerHorizontallyTo(parent)
                                top.linkTo(vBottom.bottom)
                                bottom.linkTo(parent.bottom)
                                height = Dimension.fillToConstraints
                            }
                    ) {
                        items(selectedList.value.size) {
                            JobItem(selectedList.value[it])
                        }
                    }
                }
            }
        }
    }
}