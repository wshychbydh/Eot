package cooleye.eot.kotlin.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.RadioButton
import android.widget.RadioGroup
import cooleye.eot.R
import org.jetbrains.anko.find
import org.jetbrains.anko.onCheckedChange
import org.jetbrains.anko.support.v4.onPageChangeListener


class RidingActivity : AppCompatActivity() {

    private var radiogroup: RadioGroup? = null
    private var viewpager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riding)
        var adapter = ViewPagerAdapter()
        adapter.addTab(RidingFragment::class.java, null)
        viewpager = find<ViewPager>(R.id.viewpager)
        viewpager!!.adapter = adapter
        viewpager!!.onPageChangeListener {
            onPageSelected { position -> (radiogroup!!.getChildAt(position) as RadioButton).isChecked = true }
        }
        radiogroup = find<RadioGroup>(R.id.radiogroup)
        radiogroup!!.onCheckedChange { radioGroup, checkedId ->
            viewpager!!.currentItem = radioGroup!!.indexOfChild(radioGroup!!.find(checkedId))
        }
    }

    inner class ViewPagerAdapter : FragmentPagerAdapter(supportFragmentManager){

        val tabInfos: MutableList<TabInfo> = mutableListOf()

        inner class TabInfo(internal var _clss: Class<*>, internal var _args: Bundle?) {
            internal var fragment: Fragment? = null
        }

        fun addTab(_clss: Class<*>, _args: Bundle?) {
            val info = TabInfo(_clss, _args)
            tabInfos.add(info)
            notifyDataSetChanged()
        }

        fun clear() {
            tabInfos.clear()
            notifyDataSetChanged()
        }

        override fun getCount(): Int {
            return tabInfos.size
        }

        override fun getItem(position: Int): Fragment {
            val info = tabInfos[position]
            if (info.fragment == null)
                info.fragment = Fragment.instantiate(baseContext,
                                                     info._clss.name, info._args)

            return info.fragment!!
        }
    }
}
