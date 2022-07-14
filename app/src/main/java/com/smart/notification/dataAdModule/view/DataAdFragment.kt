package com.smart.notification.dataAdModule.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.smart.notification.R
import com.smart.notification.common.adapters.RecordListAdapter
import com.smart.notification.common.utils.Constants
import com.smart.notification.common.utils.hideKeyboard
import com.smart.notification.dataAdModule.viewModel.DataAdViewModel
import com.smart.notification.databinding.FragmentDataAdBinding
import com.smart.notification.mainModule.view.MainActivity

/**
 * Project: NotificationApp
 * Package: com.smart.notification.dataAdModule.view
 * Update by israel on Thursday, 5/19/2022 6:43 PM
 * GitHub: https://github.com/FubbledIsrael
 */

class DataAdFragment : Fragment() {
    private lateinit var  mBinding: FragmentDataAdBinding
    private var mActivity: MainActivity ?= null
    private lateinit var mDataAdViewModel: DataAdViewModel
    private lateinit var mAdapter: RecordListAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataAdViewModel = ViewModelProvider(requireActivity())[DataAdViewModel::class.java]

        mAdapter = RecordListAdapter()
        mGridLayout = GridLayoutManager(requireActivity(), resources.getInteger(R.integer.counter_min_line))
        setHasOptionsMenu(Constants.SHOW)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = FragmentDataAdBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setupObservers()
    }

    private fun init() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mBinding.cardRecordAd.recycleViewRegistered.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    private fun setupObservers() {
        mDataAdViewModel.getAdSelect().observe(viewLifecycleOwner){     adCurrent ->
            mActivity?.supportActionBar?.title = adCurrent.city

            val drawable = if (adCurrent.status == 1) R.drawable.ic_circle_check else R.drawable.ic_circle_warning
            val idString = "#" + adCurrent.id.toString()

            mBinding.cardDataAd.icExpired.setImageResource(drawable)
            mBinding.cardDataAd.tvId.text = idString
            mBinding.cardDataAd.tvCity.text = adCurrent.city
            mBinding.cardDataAd.tvClassification.text = adCurrent.classification
            mBinding.cardDataAd.tvPhone.text = adCurrent.formatPhone()
            mBinding.cardDataAd.tvDevice.text = adCurrent.device
            mBinding.cardDataAd.tvExpired.text = adCurrent.expired
            mBinding.cardDataAd.tvLastUpdate.text = adCurrent.lastUpdate
        }

        mDataAdViewModel.getRecordAll().observe(viewLifecycleOwner){    recordList ->
            mBinding.progressBar.visibility = View.GONE
            mAdapter.submitList(recordList)
        }

        mDataAdViewModel.getRecordCount().observe(viewLifecycleOwner){  count ->
            mBinding.cardRecordAd.tvChats.text = count.toString()
        }

        mDataAdViewModel.getSnackBarMsg().observe(viewLifecycleOwner){      message ->
            Snackbar.make(mBinding.root, message, Snackbar.LENGTH_SHORT).show()
        }

        mDataAdViewModel.getProgressBar().observe(viewLifecycleOwner){      flag ->
            mBinding.progressBar.visibility = if(flag) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_ad, menu)
        menu.findItem(R.id.action_edit).isVisible = Constants.HIDE
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_clean -> {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle(R.string.clean)
                    .setMessage(R.string.clean_message)
                    .setPositiveButton(R.string.accept){ dialog, _ ->
                        mDataAdViewModel.removeRecordAd()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.cancel){    dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(false)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        hideKeyboard(mActivity!!, requireView())
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = mDataAdViewModel.getDevice()
        mDataAdViewModel.setShowButtonAdd(Constants.SHOW)

        setHasOptionsMenu(true)
        super.onDestroy()
    }
}