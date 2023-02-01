package com.smartyhome.app.main.home.fragment.profile

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.rmindr.app.network.MyCustomeApi
import com.smartyhome.app.R
import com.smartyhome.app.main.home.MainInterfaces
import com.smartyhome.app.main.home.roomlisting.Room
import com.smartyhome.app.main.network.ApiCallingRepository
import com.smartyhome.app.utils.LocationUtils
import com.smartyhome.app.utils.SharedPreference
import com.smartyhome.app.utils.coroutine
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), MainInterfaces {

    var preference: SharedPreference? = null

    private lateinit var edtEmail: EditText
    private lateinit var edtFname: EditText
    private lateinit var edtLname: EditText
    private lateinit var edtMo: EditText
    private lateinit var edtDob: EditText

    private lateinit var edtTimeZone: EditText
    private lateinit var edtLat: EditText
    private lateinit var edtLong: EditText
    private lateinit var edtEngCoast: EditText
    private lateinit var tvUpdateProfile: TextView
    private lateinit var llProfileContainer: LinearLayout

    var locationUtils: LocationUtils? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        preference = SharedPreference(requireContext())
        edtEmail = view.findViewById(R.id.edtEmail)
        edtFname = view.findViewById(R.id.edtFname)
        edtLname = view.findViewById(R.id.edtLname)
        edtMo = view.findViewById(R.id.edtMo)
        edtDob = view.findViewById(R.id.edtDob)
        edtTimeZone = view.findViewById(R.id.edtTimeZone)
        edtLat = view.findViewById(R.id.edtLat)
        edtLong = view.findViewById(R.id.edtLong)
        edtEngCoast = view.findViewById(R.id.edtEngCoast)
        edtEngCoast = view.findViewById(R.id.edtEngCoast)
        tvUpdateProfile = view.findViewById(R.id.tvUpdateProfile)
        llProfileContainer = view.findViewById(R.id.llProfileContainer)

        locationUtils = LocationUtils(requireContext(), this)

        setupInfo()
        onUpdate()
        return view
    }


    fun setupInfo() {
        val loginInfo = preference?.getLoginInfo()
        edtEmail.setText(loginInfo?.email)
        edtFname.setText(loginInfo?.fname)
        edtLname.setText(loginInfo?.lname)
        edtMo.setText(loginInfo?.mobile)
        edtDob.setText(loginInfo?.dob)
        edtTimeZone.setText(loginInfo?.timeZone?.toString())
        if (!loginInfo?.lat.toString().isNullOrEmpty() && !loginInfo?.lon.toString()
                .isNullOrEmpty()
        ) {
            edtLat.setText(loginInfo?.lat)
            edtLong.setText(loginInfo?.lon)
        } else {
            locationUtils?.fn_getlocation()
        }
        edtEngCoast.setText(loginInfo?.energyCost.toString())

        edtDob.setOnClickListener {
            val c = Calendar.getInstance()

            val year: Int
            val month: Int
            val day: Int

            if (edtDob.text.toString().contains("/")) {
                var split = edtDob.text.split("/")
                day = split[0].toInt()
                month = split[1].toInt().minus(1)
                year = split[2].toInt()
            } else {
                year = c.get(Calendar.YEAR)
                month = c.get(Calendar.MONTH)
                day = c.get(Calendar.DAY_OF_MONTH)
            }


            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
//                lblDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + ", " + year)
                    edtDob.setText(dayOfMonth.toString() + "/" + (monthOfYear.plus(1)) + "/" + year)
                },
                year,
                month,
                day
            )

            dpd.show()
        }
    }

    override fun setupLatLong(latitude: Double, longitude: Double) {
        edtLat.setText(latitude.toString())
        edtLong.setText(longitude.toString())
    }

    override fun onRoomClicked(roomObject: Room) {

    }

    override fun onRoomLongClicked(pos: Int) {
    }

    fun onUpdate() {
        tvUpdateProfile.setOnClickListener {
            if (edtFname.text.toString().isNullOrEmpty()) {
                edtFname.setError(getString(R.string.required))
            } else if (edtLname.text.toString().isNullOrEmpty()) {
                edtLname.setError(getString(R.string.required))
            } else if (edtDob.text.toString().isNullOrEmpty()) {
                edtDob.setError(getString(R.string.required))
            } else if (edtMo.text.toString().isNullOrEmpty()) {
                edtMo.setError(getString(R.string.required))
            } else if (edtTimeZone.text.toString().isNullOrEmpty()) {
                edtTimeZone.setError(getString(R.string.required))
                edtTimeZone.requestFocus()
            } else if (edtLat.text.toString().isNullOrEmpty()) {
                edtLat.setError(getString(R.string.required))
                edtLat.requestFocus()
            } else if (edtLong.text.toString().isNullOrEmpty()) {
                edtLong.setError(getString(R.string.required))
                edtLong.requestFocus()
            } else if (edtEngCoast.text.toString().isNullOrEmpty()) {
                edtEngCoast.setError(getString(R.string.required))
                edtEngCoast.requestFocus()
            } else {
                val myApi = MyCustomeApi()
                val _repository = ApiCallingRepository(myApi)

                val profilemodel = profilemodel(
                    edtDob.text.toString(),
                    edtEmail.text.toString(),
                    edtFname.text.toString(),
                    preference?.getLoginInfo()?.UID ?: "",
                    edtLname.text.toString(),
                    edtMo.text.toString(),
                    edtEngCoast.text.toString(),
                    edtLat.text.toString(),
                    edtLong.text.toString(),
                    edtTimeZone.text.toString()
                )
                coroutine.main {
                    val response = _repository.UpdateProfile(profilemodel)
                    if (response.body()?.success ?: false) {
                        Snackbar.make(llProfileContainer, "Success!", Snackbar.LENGTH_LONG).show()
                    } else
                        Snackbar.make(llProfileContainer, "Failed!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}