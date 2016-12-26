package cooleye.eot.kotlin.data

/**
 * Created by cool on 16-11-25.
 */
data class Riding(
        var passenger: Passenger,
        var peopleCount:Int,
        var ridingTime: String,
        var startCity: String?,
        var endCity: String?,
        var startAddress: String,
        var endAddress: String,
        var startLongitude: Double? = null,
        var startLatitude: Double?,
        var mark: String?)