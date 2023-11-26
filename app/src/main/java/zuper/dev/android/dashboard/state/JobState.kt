package zuper.dev.android.dashboard.state

import zuper.dev.android.dashboard.data.model.InvoiceModel
import zuper.dev.android.dashboard.data.model.JobModel

sealed class JobState {
    data class UpdateJobs(val jobModel: JobModel?): JobState()
}
