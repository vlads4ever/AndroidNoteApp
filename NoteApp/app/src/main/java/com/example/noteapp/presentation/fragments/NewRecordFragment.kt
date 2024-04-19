package com.example.noteapp.presentation.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
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

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody, currentDate)
            viewModel.addNote(note)
            Snackbar.make(requireView(), "Record was saved.", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_newRecordFragment_to_baseFragment)
        } else {
            Snackbar.make(requireView(), "No title specified!", Snackbar.LENGTH_SHORT).show()
        }
    }
}