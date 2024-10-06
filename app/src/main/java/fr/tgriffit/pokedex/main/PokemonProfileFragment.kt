package fr.tgriffit.pokedex.ui.main

import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import fr.tgriffit.pokedex.data.Pokemon
import fr.tgriffit.pokedex.data.auth.ApiService
import fr.tgriffit.pokedex.data.model.PkmnSharedViewModel
import fr.tgriffit.pokedex.databinding.PokemonProfileBinding
import java.util.Locale


private const val TAG = "PokemonProfileActivity"


class PokemonProfileFragment : Fragment() {
    private var pkmn: Pokemon? = null

    private lateinit var pkmnName: TextView
    private lateinit var pkmnId: TextView
    private lateinit var pkmnDesc: TextView
    private lateinit var pkmnCategory: TextView
    private lateinit var pkmnWeight: TextView
    private lateinit var pkmnHeight: TextView
    private lateinit var pkmnType: TextView
    private lateinit var pkmnAvatar: ShapeableImageView
    private var _binding: PokemonProfileBinding? = null

    private val binding get() = _binding!!
    private val sharedViewModel: PkmnSharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel.apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        _binding = PokemonProfileBinding.inflate(inflater, container, false)
        val root = binding.root

        sharedViewModel.pkmn.observe(viewLifecycleOwner) {
            if (it != null)
                updatePokemonData(it)
        }
        initPokemonProfileUIElements()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            pkmn = sharedViewModel.pkmn.value
            pkmn?.let { updatePokemonData(it) }
            //pkmn?.cursus_pkmns?.first()?.level?.let { updatePokemonLevel(it) }

        } catch (exception: Exception) {
            Log.e(TAG, "onCreate: ApiService().getMe: ", exception)
        }

        var lastSearched = ""
        //Detecte le changement de login dans la barre de recherche
        sharedViewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            if (isValidSearch(query, lastSearched)) {
                lastSearched = query
                pkmn = sharedViewModel.pkmn.value
                if (pkmn != null)
                    binding.apply { updatePokemonData(pkmn!!) }
                else {
                    val snackbar =
                        Snackbar.make(pkmnDesc, "$query not found", Snackbar.LENGTH_SHORT)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.setBackgroundTint(
                        resources.getColor(
                            android.R.color.holo_blue_dark,
                            Resources.getSystem().newTheme()
                        )
                    )
                    snackbar.show()
                }
            }

        }

        /*  sharedViewModel.currentCursus.observe(viewLifecycleOwner, Observer {
              updatePokemonLevel(it.level)
          })*/

    }

    private fun isValidSearch(login: String?, lastSearched: String): Boolean {
        if (login.isNullOrEmpty() || login.isBlank())
            return false
        if (pkmn != null && pkmn!!.name.equals(login, ignoreCase = true))
            return false
        if (lastSearched.equals(login, ignoreCase = true))
            return false
        return true
    }

    @NonNull
    private fun updatePokemonData(updatedPokemon: Pokemon = pkmn!!) {
        pkmnName.text = updatedPokemon.name.uppercase()
        pkmnId.text = String.format(Locale.US, "N° %04d", updatedPokemon.id)
        val types = "${updatedPokemon.types[0].type.name.uppercase()}${if (updatedPokemon.types.size > 1) " / ${updatedPokemon.types[1].type.name.uppercase()}" else ""}"
        pkmnType.text = String.format(Locale.US, "%s", types)
        pkmnHeight.text = String.format(Locale.US, "%.1f m",(updatedPokemon.height.toDouble() / 10))
        pkmnWeight.text = String.format(Locale.US, "%.1f kg",(updatedPokemon.weight.toDouble() / 10))

        val avatarUrl = updatedPokemon.sprites.front_default ?: Uri.parse("android.resource://fr.tgriffit.pokedex/drawable/cat").toString()

        Glide.with(this)
            .load(avatarUrl)
            .into(pkmnAvatar)

    }

    private fun initPokemonProfileUIElements() {
        pkmnName = binding.userLoginText
        pkmnId = binding.pkmnIdTxtView
        pkmnType = binding.pkmnTypesText
        pkmnDesc = binding.pkmnDescTxtView
        pkmnCategory = binding.pkmnCategoryText
        pkmnWeight = binding.pkmnWeightText
        pkmnHeight = binding.pkmnHeightText
        pkmnAvatar = binding.pkmnAvatar
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, api: ApiService): PokemonProfileFragment {
            return PokemonProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}