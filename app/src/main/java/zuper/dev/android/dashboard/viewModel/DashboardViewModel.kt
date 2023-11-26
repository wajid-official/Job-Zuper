package zuper.dev.android.dashboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zuper.dev.android.dashboard.data.DataRepository
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.InvoiceModel
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.state.DashboardState
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private var _dashboardFlow: MutableSharedFlow<DashboardState> = MutableSharedFlow()
    val dashboardFlow: SharedFlow<DashboardState> = _dashboardFlow
    private var jobData: JobModel? = null

    fun fetchJobs() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.observeJobs().collect {
                configureJobData(it)
            }
        }
    }

    private suspend fun configureJobData(jobList: List<JobApiModel>) {
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
        jobData = JobModel(
            total,
            completed,
            jobMap.toList().sortedByDescending { it.second.first }.toMap()
        )
        withContext(Dispatchers.Main){
            _dashboardFlow.emit(DashboardState.UpdateJobs(jobData))
        }
    }

    fun fetchInvoice() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.observeInvoices().collect {
                configureInvoiceData(it)
            }
        }
    }

    private suspend fun configureInvoiceData(jobList: List<InvoiceApiModel>) {
        var total = 0
        var completed = 0
        val invoiceMap = HashMap<InvoiceStatus, Pair<Int, MutableList<InvoiceApiModel>?>>()
        for (invoice in jobList) {
            if (invoiceMap.contains(invoice.status)) {
                invoiceMap[invoice.status]?.second?.add(invoice)
                invoiceMap[invoice.status] = Pair(
                    (invoiceMap[invoice.status]?.first ?: 0) + invoice.total,
                    invoiceMap[invoice.status]?.second
                )
            } else {
                invoiceMap[invoice.status] = Pair(invoice.total, mutableListOf(invoice))
            }
            if (invoice.status == InvoiceStatus.Paid)
                completed += invoice.total
            total += invoice.total
        }
        withContext(Dispatchers.Main){
            _dashboardFlow.emit(DashboardState.UpdateInvoice(
                InvoiceModel(
                total,
                completed,
                invoiceMap.toList().sortedByDescending { it.second.first }.toMap()
            )
            ))
        }
    }
}