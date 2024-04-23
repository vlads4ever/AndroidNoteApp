package com.example.noteapp.presentation.fragments

import android.app.AlertDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentFoldersListBinding
import com.example.noteapp.databinding.NewFolderQueryBinding
import com.example.noteapp.entity.Folder
import com.example.noteapp.presentation.NotesViewModel
import com.example.noteapp.presentation.adapters.FolderAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoldersListFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {
    private var _binding: FragmentFoldersListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var folderAdapter: FolderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoldersListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(this, viewLifecycleOwner)

        prepareRecyclerViewAdapter()

        viewModel.folders().observe(viewLifecycleOwner) { folders ->
            folderAdapter.submitList(folders)

            prepareVisibility(folders)
        }

        binding.addFolderButton.setOnClickListener {
            prepareAddFolderDialog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun prepareAddFolderDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.enter_folder_title))
            val folderBinding = NewFolderQueryBinding.inflate(layoutInflater)
            setView(folderBinding.root)
            setPositiveButton(getString(R.string.button_confirm_text)) { _, _ ->
                val folderName = folderBinding.folderName.text.toString().trim()
                if (folderName.isNotEmpty()) {
                    val newFolder = Folder(0, folderName)
                    viewModel.addFolder(newFolder)
                    Snackbar.make(
                        requireView(),
                        getString(R.string.notification_saved_folder),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        getString(R.string.notification_no_title),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            setNegativeButton(getString(R.string.button_cancel_text), null)
        }.create().show()
    }

    private fun prepareVisibility(folders: List<Folder>) {
        if (folders.isNotEmpty()) {
            binding.noFolderCardView.visibility = View.GONE
            binding.foldersRecyclerView.visibility = View.VISIBLE
        } else {
            binding.noFolderCardView.visibility = View.VISIBLE
            binding.foldersRecyclerView.visibility = View.GONE
        }
    }

    private fun prepareRecyclerViewAdapter() {
        folderAdapter = FolderAdapter { folder -> onItemClick(folder!!) }
        with (binding.foldersRecyclerView) {
            adapter = folderAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun onItemClick(item: Folder) {
        val bundle = Bundle().apply {
            putParcelable(FOLDER, item)
        }
        findNavController()
            .navigate(R.id.action_foldersListFragment_to_notesListFragment, bundle)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.folders_list_menu, menu)
        val menuItemSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuItemSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return menuItem.itemId == R.id.menu_search
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) searchFolder(newText)
        return true
    }

    private fun searchFolder(search: String?) {
        val searchQuery = "%$search%"
        viewModel.searchFolders(searchQuery).observe(this) { folders ->
            folderAdapter.submitList(folders)
        }
    }

    companion object {
        private const val FOLDER = "folder"
    }
}