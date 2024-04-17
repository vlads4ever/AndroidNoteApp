package com.example.noteapp.presentation

import android.app.AlertDialog
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
import com.example.noteapp.databinding.FragmentChangeRecordBinding
import com.example.noteapp.entity.Note
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeRecordFragment : Fragment(), MenuProvider {
    private var _binding: FragmentChangeRecordBinding? = null
    private val binding = _binding!!

    private val viewModel: NotesViewModel by viewModels()

    private var _note: Note? = null
    private val note = _note!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        binding.changeNoteButton.setOnClickListener {
            val noteTitle = binding.changingRecordTitle.text.toString().trim()
            val noteBody = binding.changingRecordBody.text.toString().trim()

            if (noteTitle.isNotEmpty()) {
                val newNote = Note(note.id, noteTitle, noteBody)
                viewModel.changeNote(note)
                Snackbar.make(requireView(), "Record was saved.", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_changeRecordFragment_to_baseFragment)
            } else {
                Snackbar.make(requireView(), "No title specified!", Snackbar.LENGTH_SHORT).show()
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
            setTitle("Record deleting")
            setMessage("Confirm deleting please")
            setPositiveButton("DELETE") { _, _ ->
                viewModel.deleteNote(note)
                findNavController().navigate(R.id.action_changeRecordFragment_to_baseFragment)
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }

    companion object {
        private const val NOTE = "note"
    }

}