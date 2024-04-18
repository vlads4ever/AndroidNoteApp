package com.example.noteapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentBaseBinding
import com.example.noteapp.entity.Note
import com.example.noteapp.presentation.NoteAdapter
import com.example.noteapp.presentation.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {
    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerViewAdapter()

        viewModel.getNotes().observe(viewLifecycleOwner) { notes ->
            noteAdapter.submitList(notes)

            if (notes.isNotEmpty()) {
                binding.noRecordCardView.visibility = View.GONE
                binding.notesRecyclerView.visibility = View.VISIBLE
            } else {
                binding.noRecordCardView.visibility = View.VISIBLE
                binding.notesRecyclerView.visibility = View.GONE
            }
        }

        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(R.id.action_baseFragment_to_newRecordFragment)
        }

        activity?.addMenuProvider(this, viewLifecycleOwner)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun prepareRecyclerViewAdapter() {
        noteAdapter = NoteAdapter { note -> onItemClick(note!!) }
        with (binding.notesRecyclerView) {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
        }
    }

    private fun onItemClick(item: Note) {
        val bundle = Bundle().apply {
            putParcelable(NOTE, item)
        }
        findNavController()
            .navigate(R.id.action_baseFragment_to_changeRecordFragment, bundle)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.base_menu, menu)
        val menuItemSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuItemSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return menuItem.itemId == R.id.menu_search
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) searchNote(newText)
        return true
    }

    private fun searchNote(search: String?) {
        val searchQuery = "%$search%"
        viewModel.searchNote(searchQuery).observe(this) { notes ->
            noteAdapter.submitList(notes)
        }
    }

    companion object {
        private const val NOTE = "note"
    }

}