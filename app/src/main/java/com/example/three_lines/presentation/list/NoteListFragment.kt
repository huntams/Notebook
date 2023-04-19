package com.example.three_lines.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.three_lines.R
import com.example.three_lines.databinding.FragmentNoteListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list) {
    companion object {
        private const val MOCK_TEXT =
            "Note text that resizes the card vertically to fit itself. It can be very long, but let’s settle on 180 characters as the limit"

    }

    private val binding by viewBinding(FragmentNoteListBinding::bind)
    private val viewModel by viewModels<NotesListViewModel>()
    @Inject
    lateinit var listAdapter: NoteListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotes()
        with(binding) {
            /*
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

             */
            recyclerview.apply {


                layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                adapter = listAdapter.apply {
                    setCallBack { note ->
                        Toast.makeText(requireContext(), note.text, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            floatingActionButton.setOnClickListener {
                viewModel.onAddClicked(MOCK_TEXT)
            }
            viewModel.notesListLiveData.observe(viewLifecycleOwner) {
                listAdapter.submitList(it)
            }
        }
    }
}