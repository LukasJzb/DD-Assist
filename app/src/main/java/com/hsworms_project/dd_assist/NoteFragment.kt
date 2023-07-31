package com.hsworms_project.dd_assist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.hsworms_project.dd_assist.classes.NoteViewmodel
import com.hsworms_project.dd_assist.note_data.AddNoteDialog
import com.hsworms_project.dd_assist.note_data.NoteDatabase
import com.hsworms_project.dd_assist.note_data.NoteEvent
import com.hsworms_project.dd_assist.note_data.NoteState
import com.hsworms_project.dd_assist.note_data.SortType
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class NoteFragment : Fragment() {
    var childcontext: Context = parentcontext!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val state = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_note, container, false)

        return ComposeView(requireContext()).apply { setContent { NoteScreen(state = state, onEvent = viewModel::onEvent) } }
    }
/*    private fun loadSettings(){
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val signature = sp.getString("signature", "")
    }*/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NoteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private val db by lazy {
        Room.databaseBuilder(
            childcontext,
            NoteDatabase::class.java,
            "notizen.db"
        ).build()
    }

    private val viewModel by viewModels<NoteViewmodel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewmodel(db.dao) as T
                }
            }
        }
    )
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteScreen(
        state: NoteState,
        onEvent: (NoteEvent) -> Unit
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    onEvent(NoteEvent.ShowDialog)
                }) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = "Notiz hinzufügen"
                    )
                }
            }
        ) {padding ->
            if (state.addingNote) {
                AddNoteDialog(state = state, onEvent = onEvent)
            }
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            )
            {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SortType.values().forEach { sortType ->
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        onEvent(NoteEvent.SortNotes(sortType))
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = state.sortType == sortType,
                                    onClick = {
                                        onEvent(NoteEvent.SortNotes(sortType))
                                    }
                                )
                                Text(text = sortType.name)
                            }
                        }
                    }
                }
                items(state.note) { note ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "${note.titel}",
                                fontSize = 24.sp
                            )
                            Text(text = "${note.inhalt}",
                                fontSize = 16.sp
                            )
                        }
                        IconButton(onClick = {
                            onEvent(NoteEvent.DeleteNote(note))
                        }) {
                            Icon(imageVector = Icons.Default.Delete,
                                contentDescription = "Notiz löschen"
                            )
                        }

                    }

                }
            }

        }
        state = viewModel.state.collectAsState()
    }
}

