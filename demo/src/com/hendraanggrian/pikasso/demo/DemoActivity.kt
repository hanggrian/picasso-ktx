package com.hendraanggrian.pikasso.demo

import android.os.Bundle
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.hendraanggrian.pikasso.demo.internal.SimplePanelSlideListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

    private lateinit var toggleExpandItem: MenuItem

    private val fragment = DemoFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.preferences, fragment).commit()

        panel.setScrollableView(fragment.listView)
        panel.addPanelSlideListener(object : SimplePanelSlideListener() {
            override fun onPanelStateChanged(panel: View, previous: PanelState, new: PanelState) {
                if (new == EXPANDED) {
                    toggleExpandItem.icon = getDrawable(this@DemoActivity, R.drawable.ic_collapse)
                    toggleExpandItem.title = getString(R.string.collapse)
                } else if (new == COLLAPSED) {
                    toggleExpandItem.icon = getDrawable(this@DemoActivity, R.drawable.ic_expand)
                    toggleExpandItem.title = getString(R.string.expand)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_demo, menu)
        toggleExpandItem = menu.findItem(R.id.toggleExpandItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item == toggleExpandItem) toolbar.performClick()
        return true
    }

    fun toggleExpand(view: View) {
        panel.panelState = if (panel.panelState == EXPANDED) COLLAPSED else EXPANDED
    }
}