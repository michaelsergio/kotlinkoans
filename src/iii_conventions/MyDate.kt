package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.compareTo(other: MyDate): Int {
    if (this.year < other.year) return -1
    else if (this.year > other.year) return 1
    else if (this.month < other.month) return -1
    else if (this.month > other.month) return 1
    else if (this.dayOfMonth < other.dayOfMonth) return -1
    else if (this.dayOfMonth > other.dayOfMonth) return 1
    else return 0
}

operator fun MyDate.plus(interval: TimeInterval): MyDate =
    when (interval) {
        TimeInterval.DAY -> this.addTimeIntervals(TimeInterval.DAY, 1)
        TimeInterval.WEEK -> this.addTimeIntervals(TimeInterval.WEEK, 1)
        TimeInterval.YEAR -> this.addTimeIntervals(iii_conventions .TimeInterval.YEAR, 1)
    }
operator fun MyDate.plus(multipleIntervals: MultipleTimeIntervals): MyDate {
    val quantity = multipleIntervals.quantity
    val interval = multipleIntervals.interval
    return when (interval) {
        TimeInterval.DAY -> this.addTimeIntervals(TimeInterval.DAY, quantity)
        TimeInterval.WEEK -> this.addTimeIntervals(TimeInterval.WEEK, quantity)
        TimeInterval.YEAR -> this.addTimeIntervals(iii_conventions.TimeInterval.YEAR, quantity)
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class MultipleTimeIntervals(val quantity:Int, val interval: TimeInterval)
operator fun TimeInterval.times(quantity: Int) : MultipleTimeIntervals = MultipleTimeIntervals(quantity, this)
operator fun Int.times(interval: TimeInterval) : MultipleTimeIntervals = MultipleTimeIntervals(this, interval)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterator<MyDate> {
    var dateIter: MyDate = start
    override fun next(): MyDate {
        val curDate = this.dateIter
        this.dateIter = dateIter.nextDay() // advance iterator
        return curDate
    }

    override fun hasNext(): Boolean = dateIter in this
}

operator fun DateRange.contains(date: MyDate): Boolean = start <= date && date <= endInclusive



