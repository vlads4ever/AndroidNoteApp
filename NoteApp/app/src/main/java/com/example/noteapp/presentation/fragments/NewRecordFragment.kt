package com.example.noteapp.presentation.fragments

import android.app.AlertDialog
import android.content.res.Resources.Theme
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.ColorChangeBinding
import com.example.noteapp.databinding.FragmentNewRecordBinding
import com.example.noteapp.entity.Note
import com.example.noteapp.presentation.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class NewRecordFragment : Fragment(), MenuProvider {
    private var _binding: FragmentNewRecordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(this, viewLifecycleOwner)

        binding.colorButton.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.notification_color_title))
                val colorBinding = ColorChangeBinding.inflate(layoutInflater)
                setView(colorBinding.root)
                setPositiveButton(getString(R.string.button_confirm_text), null)
                setNegativeButton(getString(R.string.button_cancel_text), null)
                colorBinding.colorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                    when(checkedId) {
                        R.id.yellow_radio_button -> binding.newRecordCardView
                            .setCardBackgroundColor(resources.getColor(R.color.yellow_100))
                        R.id.green_radio_button -> binding.newRecordCardView
                            .setCardBackgroundColor(resources.getColor(R.color.green_100))
                        R.id.blue_radio_button -> binding.newRecordCardView
                            .setCardBackgroundColor(resources.getColor(R.color.blue_100))
                    }
                }
            }.create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.new_record_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_save) {
            saveNote()
            return true
        }
        return false
    }

    private fun saveNote() {
        val noteTitle = binding.recordTitle.text.toString().trim()
        val noteBody = binding.recordBody.text.toString().trim()
        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val noteColor = binding.newRecordCardView.cardBackgroundColor.defaultColor

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody, currentDate, noteColor)
            viewModel.addNote(note)
            Snackbar.make(
                requireView(),
                getString(R.string.notification_saved_record),
                Snackbar.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_newRecordFragment_to_baseFragment)
        } else {
            Snackbar.make(
                requireView(),
                getString(R.string.notification_no_title),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}