package cooleye.eot.kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import cooleye.eot.R
import cooleye.eot.ui.widget.DividerDecoration
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.find

/**
 * Created by cool on 16-11-25.
 */
class RidingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val View = inflater?.inflate(R.layout.fragment_riding, container, false)
        return View
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        find<View>(R.id.riding_add).onClick { startActivity(Intent(activity, RidingAddActivity::class.java)) }
        val recyclerview = find<RecyclerView>(R.id.riding_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.addItemDecoration(DividerDecoration(context))
        recyclerview.adapter = RidingAdapter()
    }

    inner class RidingAdapter : RecyclerView.Adapter<RidingHolder>() {
        override fun getItemCount(): Int {
            return 4
        }

        override fun onBindViewHolder(holder: RidingHolder, position: Int) {
            holder.name.text = "name"
            holder.phone.text = "1323575573"
            holder.start_address.text = "赶快来大家靠公司"
            holder.end_address.text = "对是否考虑的说法老师"
            holder.start_time.text = "2015-11-25 5:44"
            holder.item.id = position
            holder.item.onClick { v -> Toast.makeText(context, "position->" + v!!.id, Toast.LENGTH_SHORT).show() }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RidingHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.riding_item, null)
            return RidingHolder(view)
        }
    }

    inner class RidingHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.find<TextView>(R.id.name)
        val phone = view.find<TextView>(R.id.phone)
        val start_address = view.find<TextView>(R.id.start_address)
        val end_address = view.find<TextView>(R.id.end_address)
        val start_time = view.find<TextView>(R.id.start_time)
        val item = view.find<View>(R.id.riding_item)
    }
}