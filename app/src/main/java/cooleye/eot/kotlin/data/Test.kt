package cooleye.eot.kotlin.data

/**
 * Created by cool on 16-11-29.
 */
open class Test {
    lateinit var test: String

    fun setValue(value: String) {
        test = value
    }

    fun getValue(): String {
        return test
    }

}