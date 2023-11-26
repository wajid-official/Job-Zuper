package zuper.dev.android.dashboard.data.model

data class InvoiceModel(
    val total: Int,
    val collected: Int,
    val invoiceMap: Map<InvoiceStatus, Pair<Int, MutableList<InvoiceApiModel>?>>
)