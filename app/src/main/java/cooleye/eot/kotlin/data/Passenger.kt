package cooleye.eot.kotlin.data

/**
 * Created by cool on 16-11-25.
 */
data class Passenger(
        var name: String = "",
        var sex: SEX = Passenger.SEX.UNFILLED,
        var body: Body = Passenger.Body.UNFILLED,
        var age: AGE = Passenger.AGE.UNFILLED) {

    enum class SEX {
        UNFILLED, MAN, WOMAN
    }

    enum class Body {
        UNFILLED, FAT, NORMAL, THIN
    }

    enum class AGE {
        UNFILLED, /*0~10*/ SMALL, /*11~55*/ YOUNG, /*55~*/ OLD
    }

    fun test(){
        val p = Passenger()
        val np = p.copy()
        np.sex = SEX.MAN
        var (name,sex,body,age) = p
        println("$age,$name")

    }
}