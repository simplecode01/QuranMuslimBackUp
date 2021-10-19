package com.simplecode01.quranmuslim.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.databinding.FragmentSearchQuranBinding
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.simplecode01.quranmuslim.data.QuranDatabase
import com.simplecode01.quranmuslim.ui.quran.QuranViewModel
import com.simplecode01.quranmuslim.ui.quran.ReadQuranAdapter

class SearchQuran: Fragment(R.layout.fragment_search_quran) {
    private val binding: FragmentSearchQuranBinding by viewBinding()
    private val viewModel: QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showOrHideKeyboard(true)
        binding.inputTextSearch.addTextChangedListener {text ->

        }
        binding.inputTextSearch.setOnEditorActionListener { inputText, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val query = binding.inputTextSearch.text.toString()
                setQuranAdapter(query)
                true

            }
            false
        }
        binding.toolbar.setNavigationOnClickListener {
            //memngembalikan kembali ke UI
            findNavController().navigateUp()
            showOrHideKeyboard(false)
        }
    }

    private  fun setQuranAdapter(query: String){
        val quranDao = QuranDatabase.getInstance(requireContext()).quranDao()
        viewModel.getTotalAyahList().observe(viewLifecycleOwner, { totalAyahlist ->
            quranDao.searchAyat("%$query%").asLiveData().observe(viewLifecycleOwner,{quranList->
                val adapter = ReadQuranAdapter(quranList, totalAyahlist)
                Log.d("CEKQURAN LIST", quranList.size.toString())
                binding.recyclerview.adapter = adapter
            })
        })

    }

    private fun showOrHideKeyboard(show: Boolean){
        val imm: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if(show){//Show keyboard
            binding.inputTextSearch.requestFocus()
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }else{//Hide Keyboard
            binding.inputTextSearch.clearFocus()
            imm.hideSoftInputFromWindow(binding.inputTextSearch.getWindowToken(), 0)
        }
    }

}