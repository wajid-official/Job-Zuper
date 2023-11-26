package zuper.dev.android.dashboard.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class JobModel(
    val total: Int,
    val completed: Int,
    val jobMap: Map<JobStatus, Pair<Int, MutableList<JobApiModel>?>>
)