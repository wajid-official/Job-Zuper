package zuper.dev.android.dashboard.state

import zuper.dev.android.dashboard.data.model.InvoiceModel
import zuper.dev.android.dashboard.data.model.JobModel

sealed class DashboardState {
    data class UpdateJobs(val jobModel: JobModel?): DashboardState()
    data class UpdateInvoice(val invoiceModel: InvoiceModel?): DashboardState()
}
