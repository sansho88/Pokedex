package fr.tgriffit.pokedex.ui.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.tgriffit.pokedex.data.model.PkmnSharedViewModel
import fr.tgriffit.pokedex.databinding.FragmentSkillsListBinding
import fr.tgriffit.pokedex.main.SkillsViewAdapter
import fr.tgriffit.pokedex.ui.main.PokemonProfileFragment.Companion.ARG_SECTION_NUMBER

/**
 * A fragment representing a list of Items.
 */
class SkillsFragment : Fragment() {

    private var columnCount = 1
    private lateinit var adapter: SkillsViewAdapter
    private lateinit var recyclerView: RecyclerView
    private val sharedViewModel: PkmnSharedViewModel by activityViewModels()
    private var _binding: FragmentSkillsListBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel.apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 2)
        }
        _binding = FragmentSkillsListBinding.inflate(inflater, container, false)
        val root = binding.root

        recyclerView = binding.list
        if (sharedViewModel.pkmn.value != null && sharedViewModel.version.value != null)
            sharedViewModel.setSkillsList(sharedViewModel.pkmn.value!!.moves)
        adapter = SkillsViewAdapter(sharedViewModel.skillsList.value)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        sharedViewModel.version.observe(viewLifecycleOwner) { version ->
            Log.d("Version Observer", "New version: ${version.name}")
            val skillsList = sharedViewModel.skillsList.value?.filter { move ->
                move.version_group_details.find { groupDetail ->
                    groupDetail.version_group.name.contains(version.name)
                            && groupDetail.move_learn_method.name == "level-up"
                } != null
            }?.sortedBy { it.version_group_details[0].level_learned_at }
            Log.d("Version Observer", "List skills size filtered: ${skillsList?.size} vs ${sharedViewModel.skillsList.value?.size} (non-filtered)")

            adapter = SkillsViewAdapter(skillsList)
            recyclerView.adapter = adapter
        }
        return root
    }


    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            SkillsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}