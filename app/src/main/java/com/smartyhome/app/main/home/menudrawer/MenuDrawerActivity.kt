package com.smartyhome.app.main.home.menudrawer

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView.OnMenuClickListener
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.TextView
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.smartyhome.app.R
import com.smartyhome.app.main.SplashActivity
import com.smartyhome.app.main.forgetpass.ResetPassActivity
import com.smartyhome.app.main.home.fragment.home.HomeFragment
import com.smartyhome.app.main.home.MenuAdapter
import com.smartyhome.app.main.home.fragment.profile.ProfileFragment
import com.smartyhome.app.utils.Constant
import com.smartyhome.app.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_verify_mobile.*
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView
import java.util.*

class MenuDrawerActivity : AppCompatActivity(), OnMenuClickListener {
    private var mMenuAdapter: MenuAdapter? = null
    private var mViewHolder: ViewHolder? = null
    private var mTitles = ArrayList<String>()
    var preference: SharedPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_drawer)
        mTitles = ArrayList(Arrays.asList(*resources.getStringArray(R.array.menuOptions)))

        // Initialize the views
        mViewHolder = ViewHolder()

        // Handle toolbar actions
        handleToolbar()

        // Handle menu actions
        handleMenu()

        // Handle drawer actions
        handleDrawer()

        // Show main fragment in container
        goToFragment(HomeFragment(), false)
        mMenuAdapter!!.setViewSelected(0, true)
        title = mTitles[0]
        preference = SharedPreference(this)
    }

    private fun handleToolbar() {
        setSupportActionBar(mViewHolder!!.mToolbar)
    }

    private fun handleDrawer() {
        val DrawerToggle = DuoDrawerToggle(
            this,
            mViewHolder!!.mDrawerLayout,
            mViewHolder!!.mToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        mViewHolder!!.mDrawerLayout.setDrawerListener(DrawerToggle)

        mViewHolder!!.mToolbar.setNavigationIcon(R.drawable.ic_vector_menu)

        DrawerToggle.syncState()
    }

    private fun handleMenu() {
        mMenuAdapter = MenuAdapter(mTitles)
        mViewHolder!!.mDuoMenuView.setOnMenuClickListener(this)
        mViewHolder!!.mDuoMenuView.adapter = mMenuAdapter
    }

    override fun onFooterClicked() {
        mViewHolder!!.mDrawerLayout.closeDrawer()
        showDialog(getString(R.string.logout),getString(R.string.logout_msg))
    }

    override fun onHeaderClicked() {
    // Close the drawer
        mViewHolder!!.mDrawerLayout.closeDrawer()
    }

    private fun goToFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.replace(R.id.container, fragment).commit()
    }

    override fun onOptionClicked(position: Int, objectClicked: Any) {
        // Set the toolbar title
        if (position != 3) {
            title = mTitles[position]
            mMenuAdapter!!.setViewSelected(position, true)
            // Close the drawer

            supportActionBar?.setTitle(title)
        }
        // Set the right options selected
        when (position) {
            0 -> goToFragment(HomeFragment(), true)
            1 -> goToFragment(ProfileFragment(), false)
            3-> openChangePassScreen()
        }
        mViewHolder?.mDrawerLayout?.closeDrawer()
    }

    private inner class ViewHolder internal constructor() {
        val mDrawerLayout: DuoDrawerLayout
        val mDuoMenuView: DuoMenuView
        val mToolbar: Toolbar

        init {
            mDrawerLayout = findViewById<View>(R.id.drawer) as DuoDrawerLayout
            mDuoMenuView = mDrawerLayout.menuView as DuoMenuView
            mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun showDialog(title: String,msg:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_confirm_dialog)
        val tvTitle = dialog.findViewById(R.id.tvTitle) as TextView
        val body = dialog.findViewById(R.id.tvMsg) as TextView
        tvTitle.text = title
        body.text = msg
        val yesBtn = dialog.findViewById(R.id.btnYes) as TextView
        val noBtn = dialog.findViewById(R.id.btnNo) as TextView
        yesBtn.setOnClickListener {
            var sharedPreference: SharedPreference = SharedPreference(this@MenuDrawerActivity)
            sharedPreference.setString(Constant.LOGIN_INFO, "")
            dialog.dismiss()
            startActivity(Intent(MenuDrawerActivity@ this, SplashActivity::class.java))
            finish()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    fun openChangePassScreen() {
        val loginInfo = preference?.getLoginInfo()
        var intent = Intent(this, ResetPassActivity::class.java)
        intent.setAction("fromReset")
        intent.putExtra("mobileNumber", loginInfo?.mobile)
        intent.putExtra("otpCode", "")
        startActivity(intent)
    }
}