package com.example.RoomRepositoryVModelSetup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.RoomRepositoryVModelSetup.adapter.UserDataAdapter
import com.example.RoomRepositoryVModelSetup.appDatabase.AppDataBase
import com.example.RoomRepositoryVModelSetup.dialogFragment.UserDetailsDialog
import com.example.RoomRepositoryVModelSetup.model.Datum
import com.example.RoomRepositoryVModelSetup.model.UserResponse
import com.example.RoomRepositoryVModelSetup.repository.UserRepository
import com.example.RoomRepositoryVModelSetup.viewmodel.UserViewModel
import com.example.RoomRepositoryVModelSetup.viewmodel.UserViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*



class FragmentHome : Fragment() {
    private val TAG = "FragmentHome"
    private var rvUserData : RecyclerView ? = null
    private var adapter : UserDataAdapter ?= null
    private var localDb : AppDataBase?=null
    private var userViewModel : UserViewModel ?=null
    private var userRepo: UserRepository?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localDb = AppDataBase.getDatabaseInstance(requireActivity())
        userRepo = UserRepository(localDb?.getUserDao()!!)
        userViewModel = ViewModelProviders.of(
            this,
            UserViewModelFactory(
                userRepo
            )
        ).get(UserViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        loadContent()

    }

    private fun loadContent() {
        Log.d(TAG, "LoadContent")
        val userDataList = userViewModel?.getUserDatafromLocalDb()
        Log.d(TAG, "Size of list before api call ${userDataList?.size}")
        var fetchedDataList = ArrayList<Datum>()

        if(userDataList==null || userDataList.size==0){
            if(Utils.isNetworkAvailable(requireActivity())){
                fetchData()
            }else{
                Toast.makeText(activity, "Network not avaialeble", Toast.LENGTH_SHORT).show()
            }

        }else {
            //compare two lists data
             if(fetchedDataList.containsAll(userDataList)){
                 //data already presents
             }else{
                 fetchData()
             }
        }
    }

    private fun fetchData() {
        userViewModel?.getUserData()
        val userDataObserver = Observer<UserResponse>{
            adapter?.add(it.getData())
            userViewModel?.clearTable()
            saveToLocalDatabase(it.getData())
        }
        userViewModel?.getUserResponseData()?.observe(requireActivity(), userDataObserver)

    }

    private fun saveToLocalDatabase(data: List<Datum?>?) {
        Log.d(TAG, "Size of Data List is ${data?.size}")
        if(data !=null && data.size !=0){
            for(obj in data){
                userViewModel?.insertUserData(obj)
            }

        }
    }

    private fun initialize() {

        rvUserData = rv_userData
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvUserData?.layoutManager = layoutManager
        adapter = UserDataAdapter(requireActivity(), { userId ->
            UserDetailsDialog
                .newInstance(userId, userRepo, userViewModel)
                .show(requireActivity().supportFragmentManager, "USER_DETAILS_DIALOG")
        })
        rvUserData?.adapter = adapter
    }

    companion object {
        fun newInstance() =
                FragmentHome()
    }


}