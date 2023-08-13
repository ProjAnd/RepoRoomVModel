package com.example.RoomRepositoryVModelSetup.dialogFragment

import android.app.Service
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.RoomRepositoryVModelSetup.R
import com.example.RoomRepositoryVModelSetup.model.User
import com.example.RoomRepositoryVModelSetup.repository.UserRepository
import com.example.RoomRepositoryVModelSetup.service.Servicebind
import com.example.RoomRepositoryVModelSetup.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.layout_user_details.*

class UserDetailsDialog : DialogFragment() {
    private val TAG = "UserDetailsDialog"
    private var userId: Int=0
    private var tvTimer: TextView?=null
    private var tvUserId: TextView?=null
    private var tvUserName: TextView?=null
    private var tvUserLastName: TextView?=null
    private var tvUserEmail: TextView?=null
    private var serviceBind : Servicebind?=null
    private var receiver : Receiver ?=null
    private var userViewModel : UserViewModel?=null
    private var userRepo: UserRepository?=null

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: Servicebind.MyBinder = service as Servicebind.MyBinder

            serviceBind = binder.getServiceInstance()
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate is invoked")

        setStyle(STYLE_NO_FRAME, R.style.styleDialogAlert)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                dialog?.window?.setStatusBarColor(resources.getColor(R.color.colorTheme))
            }

        val serviceIntent  =  Intent(requireActivity(), Servicebind::class.java)
        activity?.bindService(serviceIntent, serviceConnection , Service.BIND_AUTO_CREATE)

        val intentFilter: IntentFilter = IntentFilter()
        intentFilter.addAction(Servicebind.MY_ACTION)
        receiver = Receiver()
        activity?.registerReceiver(receiver, intentFilter)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated is invoked")

        initialize()
        loadContent()
    }

    private fun loadContent() {
        userViewModel?.getUserDetailsDataApi(userId)
        userViewModel?.getuserDetailsData()
        val userDataObserver = Observer<User>{
            Log.d(TAG, "User Details Data has been fetched${it.data}")
            if(it.data!=null){
                val userData = it.data
                tvUserId?.text = userData?.getId().toString()
                tvUserName?.text = userData?.getFirstName()
                tvUserLastName?.text = userData?.getLastName()
                tvUserEmail?.text = userData?.getEmail()
            }
        }
        userViewModel?.getuserDetailsData()?.observe(requireActivity(), userDataObserver)

    }

    private fun initialize() {
        tvTimer = tv_timer
        tvUserName = tv_user_name
        tvUserLastName = tv_user_last_name
        tvUserEmail = tv_user_email
        tvUserId = tv_user_id

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView is invoked")

        return inflater.inflate(R.layout.layout_user_details, container, false)
    }

    companion object{
        fun newInstance(userId: Int, userRepo: UserRepository?, userViewModel: UserViewModel?): UserDetailsDialog {
            val instance = UserDetailsDialog()
            instance.userViewModel = userViewModel
            instance.userRepo = userRepo
            instance.userId = userId
            return instance
        }

    }


    inner class Receiver : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            var count = p1?.getIntExtra("COUNT", 0)
            tvTimer?.text = count.toString()
            if(count==6){
                dismiss()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onResume is invoked")
        activity?.unbindService(serviceConnection)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume is invoked")
        activity?.bindService(Intent(requireActivity(), Servicebind::class.java), serviceConnection, Service.BIND_AUTO_CREATE)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(TAG, "onDismiss is invoked")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView is invoked")

    }



}