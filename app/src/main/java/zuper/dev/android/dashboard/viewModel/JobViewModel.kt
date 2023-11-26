package zuper.dev.android.dashboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.state.JobState
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private var _jobFlow: MutableSharedFlow<JobState> = MutableSharedFlow()
    val jobFlow: SharedFlow<JobState> = _jobFlow
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun updateJobs(job: JobModel) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = false
            _jobFlow.emit(JobState.UpdateJobs(job))
        }
    }

    fun fetchJobs() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            dataRepository.observeJobs().take(1).collect {
                configureJobData(it)
            }
        }
    }

    private fun configureJobData(jobList: List<JobApiModel>) {
        var total = 0
        var completed = 0
        val jobMap = HashMap<JobStatus, Pair<Int, MutableList<JobApiModel>?>>()
        for (job in jobList) {
            if (jobMap.contains(job.status)) {
                jobMap[job.status]?.second?.add(job)
                jobMap[job.status] = Pair(
                    (jobMap[job.status]?.first ?: 0) + 1,
                    jobMap[job.status]?.second
                )
            } else {
                jobMap[job.status] = Pair(1, mutableListOf(job))
            }
            if (job.status == JobStatus.Completed)
                completed++
            total++
        }

        updateJobs(
            JobModel(
                total,
                completed,
                jobMap.toList().sortedByDescending { it.second.first }.toMap()
            )
        )
    }
}