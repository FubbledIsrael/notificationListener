package com.smart.notification.dataAdModule.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

class DataAdFragment : Fragment(R.layout.fragment_data_ad) {
    private lateinit var mDataAdBinding: FragmentDataAdBinding
    private var mActivity: MainActivity ?= null
    private lateinit var mDataAdViewModel: DataAdViewModel
    private lateinit var mAdapterRecord: RecordListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataAdViewModel = ViewModelProvider(requireActivity())[DataAdViewModel::class.java]

        setHasOptionsMenu(Constants.SHOW)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        setupObservers()
        setupButtons()
    }

    private fun init(view: View) {
        mDataAdBinding = FragmentDataAdBinding.bind(view)

        mAdapterRecord = RecordListAdapter()
        mDataAdBinding.cardRecordAd.recycleView.apply {
            adapter = mAdapterRecord
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
        }

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mActivity?.showRecyclerView(Constants.HIDE, Constants.HIDE)
    }

    private fun setupObservers() {
        mDataAdViewModel.getAdSelect().observe(viewLifecycleOwner){     adCurrent ->
            mActivity?.supportActionBar?.title = adCurrent.city

            val drawable = if (adCurrent.status == 1) R.drawable.ic_circle_check else R.drawable.ic_circle_warning
            val idString = "#" + adCurrent.id.toString()

            mDataAdBinding.cardDataAd.icExpired.setImageResource(drawable)
            mDataAdBinding.cardDataAd.tvId.text = idString
            mDataAdBinding.cardDataAd.tvCity.text = adCurrent.city
            mDataAdBinding.cardDataAd.tvClassification.text = adCurrent.classification
            mDataAdBinding.cardDataAd.tvPhone.text = adCurrent.formatPhone()
            mDataAdBinding.cardDataAd.tvDevice.text = adCurrent.device
            mDataAdBinding.cardDataAd.tvExpired.text = adCurrent.expired.ifEmpty { getString(R.string.n_a) }
            mDataAdBinding.cardDataAd.tvLastUpdate.text = adCurrent.lastUpdate.ifEmpty { getString(R.string.n_a) }
        }

        mDataAdViewModel.getRecordAll().observe(viewLifecycleOwner){    recordList ->
            recordList.sortByDescending {     record ->
                record.time
            }

            mAdapterRecord.submitList(recordList)

            mDataAdBinding.progressBar.visibility = View.GONE
        }

        mDataAdViewModel.getRecordCount().observe(viewLifecycleOwner){  count ->
            mDataAdBinding.cardRecordAd.tvChats.text = count.toString()

            mDataAdBinding.cardRecordAd.icClean.visibility = if(count > 0) View.VISIBLE else  View.GONE
        }
        mDataAdViewModel.getProgressBar().observe(viewLifecycleOwner){      flag ->
            mDataAdBinding.progressBar.visibility = if(flag) View.VISIBLE else View.GONE
        }
    }

    private fun setupButtons() {
        mDataAdBinding.cardRecordAd.icClean.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
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
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_ad, menu)
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
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
        mActivity?.showRecyclerView(Constants.SHOW, mDataAdViewModel.getShowButtonAdd())

        setHasOptionsMenu(Constants.SHOW)
        super.onDestroy()
    }
}