package zuper.dev.android.dashboard.snippet


fun Int.getPercent(value: Int): Float {
    return (value/this).toFloat()
}