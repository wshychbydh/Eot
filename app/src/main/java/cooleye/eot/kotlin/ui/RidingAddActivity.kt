package cooleye.eot.kotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import cooleye.eot.R
import cooleye.eot.kotlin.data.Passenger
import cooleye.eot.kotlin.data.Riding
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onItemSelectedListener

class RidingAddActivity : AppCompatActivity() {

    var nameTxt: EditText? = null
    var startEt: EditText? = null
    var endEt: EditText? = null
    var timeTxt: TextView? = null
    var countSp: AppCompatSpinner? = null
    var markEt: EditText? = null

    val peopleCounts = listOf(1, 2, 3, 4, 5)
    var peopleCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_riding)

        nameTxt = find(R.id.name)
        startEt = find(R.id.start_address)
        endEt = find(R.id.end_address)
        timeTxt = find(R.id.start_time)
        countSp = find(R.id.people_count)
        markEt = find(R.id.mark)

        countSp!!.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, peopleCounts)
        countSp!!.onItemSelectedListener {
            onItemSelected { parent, view, position, id ->
                peopleCount = peopleCounts[position]
            }
        }

        find<View>(R.id.back).onClick { finish() }
        find<View>(R.id.submit).onClick { submit() }
    }

    fun submit() {

        val name = nameTxt!!.text.toString()
        if (name.isEmpty()) {
            Toast.makeText(this, "名字不能为空", Toast.LENGTH_SHORT).show()
            return
        }

        val startAds = startEt!!.text.toString()
        if (startAds.isEmpty()) {
            Toast.makeText(this, "出发地址不能为空", Toast.LENGTH_SHORT).show()
            return
        }

        val endAds = endEt!!.text.toString()
        if (endAds.isEmpty()) {
            Toast.makeText(this, "目的地址不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Riding(passenger = Passenger(name),
                          peopleCount = peopleCount,
                          ridingTime = timeTxt!!.text.toString(),
                          startAddress = startAds,
                          endAddress = endAds,
                          mark = markEt!!.text.toString(),
                          startCity = null,
                          endCity = null,
                          startLatitude = null,
                          startLongitude = null)
        Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show()
    }
}
