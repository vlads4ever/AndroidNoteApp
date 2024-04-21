package com.example.noteapp.presentation.fragments

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.databinding.ColorChangeBinding
import com.example.noteapp.databinding.FragmentChangeRecordBinding
import com.example.noteapp.entity.Note
import com.example.noteapp.presentation.NotesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ChangeRecordFragment : Fragment(), MenuProvider {
    private var _binding: FragmentChangeRecordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()

    private var _note: Note? = null
    private val note get() = _note!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            _note = it.getParcelable(NOTE)!!
        }

        activity?.addMenuProvider(this, viewLifecycleOwner)

        binding.changingRecordTitle.setText(note.noteTitle)
        binding.changingRecordBody.setText(note.noteBody)
        binding.changingRecordDate.text = note.noteData
        binding.changingRecordCardView.setCardBackgroundColor(note.noteColor)

        binding.changingColorButton.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.notification_color_title))
                val colorBinding = ColorChangeBinding.inflate(layoutInflater)
                setView(colorBinding.root)
                setPositiveButton(getString(R.string.button_confirm_text), null)
                setNegativeButton(getString(R.string.button_cancel_text), null)
                colorBinding.colorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                    when(checkedId) {
                        R.id.yellow_radio_button -> binding.changingRecordCardView
                            .setCardBackgroundColor(resources.getColor(R.color.yellow_100))
                        R.id.green_radio_button -> binding.changingRecordCardView
                            .setCardBackgroundColor(resources.getColor(R.color.green_100))
                        R.id.blue_radio_button -> binding.changingRecordCardView
                            .setCardBackgroundColor(resources.getColor(R.color.blue_100))
                    }
                }
            }.create().show()
        }

        binding.changeNoteButton.setOnClickListener {
            val noteTitle = binding.changingRecordTitle.text.toString().trim()
            val noteBody = binding.changingRecordBody.text.toString().trim()
            val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
            val noteColor = binding.changingRecordCardView.cardBackgroundColor.defaultColor

            if (noteTitle.isNotEmpty()) {
                val newNote = Note(note.id, noteTitle, noteBody, currentDate, noteColor)
                viewModel.changeNote(newNote)
                Snackbar.make(
                    requireView(),
                    getString(R.string.notification_saved_record),
                    Snackbar.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_changeRecordFragment_to_baseFragment)
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.notification_no_title),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.change_record_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            deleteNote()
            return true
        }
        return false
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.notification_delete_title))
            setMessage(getString(R.string.notification_delete_body))
            setPositiveButton(getString(R.string.button_delete_text)) { _, _ ->
                viewModel.deleteNote(note)
                findNavController().navigate(R.id.action_changeRecordFragment_to_baseFragment)
            }
            setNegativeButton(getString(R.string.button_cancel_text), null)
        }.create().show()
    }

    companion object {
        private const val NOTE = "note"
    }

}