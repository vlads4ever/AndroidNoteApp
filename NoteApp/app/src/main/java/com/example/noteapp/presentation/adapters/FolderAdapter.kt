package com.example.noteapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.FolderCardBinding
import com.example.noteapp.entity.Folder

class FolderAdapter(
    private val onClick: (Folder?) -> Unit,
    private val onDelete: (Folder?) -> Unit,
    private val onRename: (Folder?) -> Unit
) : ListAdapter<Folder, FolderHolder>(FoldersDiffUtilCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderHolder {
        val binding = FolderCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FolderHolder(binding)
    }

    override fun onBindViewHolder(holder: FolderHolder, position: Int) {
        val currentFolder = getItem(position)

        holder.binding.folderCardTitle.text = currentFolder.folderTitle

        holder.binding.root.setOnClickListener {
            currentFolder?.let {
                onClick(currentFolder)
            }
        }

        holder.binding.deleteFolderButton.setOnClickListener {
            currentFolder?.let {
                onDelete(currentFolder)
            }
        }

        holder.binding.renameFolderButton.setOnClickListener {
            currentFolder?.let {
                onRename(currentFolder)
            }
        }
    }

}

class FoldersDiffUtilCallback : DiffUtil.ItemCallback<Folder>() {
    override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean =
        oldItem == newItem
}

class FolderHolder(val binding: FolderCardBinding) : RecyclerView.ViewHolder(binding.root)