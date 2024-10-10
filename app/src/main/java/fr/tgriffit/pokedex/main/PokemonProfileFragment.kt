package fr.tgriffit.pokedex.ui.main

import android.content.res.ColorStateList
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
import fr.tgriffit.pokedex.R
import fr.tgriffit.pokedex.data.DescriptionPokemon
import fr.tgriffit.pokedex.data.Pokemon
import fr.tgriffit.pokedex.data.auth.ApiService
import fr.tgriffit.pokedex.data.model.PkmnSharedViewModel
import fr.tgriffit.pokedex.data.model.Version
import fr.tgriffit.pokedex.data.model.VersionSprites
import fr.tgriffit.pokedex.databinding.PokemonProfileBinding
import java.util.Locale


private const val TAG = "PokemonProfileActivity"


class PokemonProfileFragment : Fragment() {
    private lateinit var pkmnName: TextView
    private lateinit var pkmnId: TextView
    private lateinit var pkmnDesc: TextView
    private lateinit var pkmnCategory: TextView
    private lateinit var pkmnWeight: TextView
    private lateinit var pkmnHeight: TextView
    private lateinit var pkmnType: TextView
    private lateinit var pkmnAvatar: ShapeableImageView
    private var avatarUrl: String? = null
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
        initPokemonProfileUIElements()
        updatePokemonData(sharedViewModel.pkmn.value)

        sharedViewModel.pkmn.observe(viewLifecycleOwner) {
            updatePokemonData(it)
            val tmpDesc = sharedViewModel.getPkmnDesc(it)
            if (tmpDesc != null)
                sharedViewModel.setDescPkmn(tmpDesc)
        }

        sharedViewModel.descPkmn.observe(viewLifecycleOwner) {
            if (it != null)
                updateDescription(it)
        }

        sharedViewModel.version.observe(viewLifecycleOwner) { version ->
            updateDescription(sharedViewModel.descPkmn.value!!)
            updateAvatar(getUrlAvatarFromGeneration(version, sharedViewModel.pkmn.value!!.sprites.versions))
        }

        return root
    }


    @NonNull
    private fun updatePokemonData(updatedPokemon: Pokemon?) {
        if (updatedPokemon == null) {
            pkmnName.text = "Pokemon not found"
            pkmnId.text = ""
            pkmnType.text = "Please check your connexion"
            pkmnDesc.text = ""
            pkmnCategory.text = ""
            pkmnWeight.text = ""
            pkmnHeight.text = ""
            pkmnAvatar.alpha = 0f
            return
        }

        pkmnName.text = updatedPokemon.name.uppercase()
        pkmnId.text = String.format(Locale.US, "N° %04d", updatedPokemon.id)

        val types =
            "${updatedPokemon.types.get(0).type.name.uppercase()}${if (updatedPokemon.types.size > 1) " / ${updatedPokemon.types[1].type.name.uppercase()}" else ""}"
        pkmnType.text = String.format(Locale.US, "%s", types)
        pkmnHeight.text =
            String.format(Locale.US, "%.1f m", (updatedPokemon.height.toDouble() / 10))
        pkmnWeight.text =
            String.format(Locale.US, "%.1f kg", (updatedPokemon.weight.toDouble() / 10))

        avatarUrl = updatedPokemon.sprites.front_default
        updateAvatar(avatarUrl)

    }

    private fun updateDescription(description: DescriptionPokemon) {
        val version = sharedViewModel.version.value
        pkmnDesc.text = description.flavor_text_entries.find { flavor ->
            flavor.language.name == "en" && flavor.version.name == version?.name
        }?.flavor_text?.replace("\n", " ")

        pkmnCategory.text = description.genera.find { genera ->
            genera.language.name == "en"
        }?.genus
    }

    private fun updateAvatar(url: String?) {
        if (url == null) {
            pkmnAvatar.alpha = 0f
            return
        }
        pkmnAvatar.alpha = 1f
        pkmnAvatar.strokeWidth = 2f
        pkmnAvatar.strokeColor = ColorStateList.valueOf(
            resources.getColor(
                R.color.app_theme_secondary_color,
                context?.theme
            )
        )
        Glide.with(this)
            .load(url)
            .centerInside()
            .into(pkmnAvatar)
    }

    private fun getUrlAvatarFromGeneration(version: Version, versionSprites: VersionSprites): String{
        var url = versionSprites.let { spriteVersion ->
            when (version.name) {
                "yellow" -> spriteVersion.generationI.yellow.front_default
                "red" -> spriteVersion.generationI.red_blue.front_default
                "blue" -> spriteVersion.generationI.red_blue.front_default
                "gold" -> spriteVersion.generationii.gold.front_default
                "silver" -> spriteVersion.generationii.silver.front_default
                "crystal" -> spriteVersion.generation_iii.emerald.front_default
                "firered" -> spriteVersion.generation_iii.firered_leafgreen.front_default
                "leafgreen" -> spriteVersion.generation_iii.firered_leafgreen.front_default
                "ruby" -> spriteVersion.generation_iii.ruby_sapphire.front_default
                "sapphire" -> spriteVersion.generation_iii.ruby_sapphire.front_default
                "diamond" -> spriteVersion.generation_iv.diamond_pearl.front_default
                "pearl" -> spriteVersion.generation_iv.diamond_pearl.front_default
                "platinum" -> spriteVersion.generation_iv.platinum.front_default
                "heartgold" -> spriteVersion.generation_iv.heartgold_soulsilver.front_default
                "soulsilver" -> spriteVersion.generation_iv.heartgold_soulsilver.front_default
                "black" -> spriteVersion.generation_v.black_white.front_default
                "white" -> spriteVersion.generation_v.black_white.front_default
                "black-2" -> spriteVersion.generation_v.black_white.front_default
                "white-2" -> spriteVersion.generation_v.black_white.front_default
                "x" -> spriteVersion.generation_vi.xY.front_default
                "y" -> spriteVersion.generation_vi.xY.front_default
                "omega-ruby" -> spriteVersion.generation_vi.omegaruby_alphasapphire.front_default
                "alpha-sapphire" -> spriteVersion.generation_vi.omegaruby_alphasapphire.front_default
                "sun" -> spriteVersion.generation_vii.ultra_sun_ultra_moon.front_default
                "moon" -> spriteVersion.generation_vii.ultra_sun_ultra_moon.front_default
                "ultra-sun" -> spriteVersion.generation_vi.xY.front_default
                "ultra-moon" -> spriteVersion.generation_vi.xY.front_default
                "sword" -> sharedViewModel.pkmn.value!!.sprites.other.showdown.front_default
                "shield" -> sharedViewModel.pkmn.value!!.sprites.other.showdown.front_default
                "lets-go" -> sharedViewModel.pkmn.value!!.sprites.other.showdown.front_default
                "lets-go-pikachu" -> sharedViewModel.pkmn.value!!.sprites.other.showdown.front_default
                "lets-go-eevee" -> sharedViewModel.pkmn.value!!.sprites.other.showdown.front_default
                "sword-shield" -> sharedViewModel.pkmn.value!!.sprites.other.showdown.front_default
                "legends-arceus" -> sharedViewModel.pkmn.value!!.sprites.other.showdown.front_default

                else -> {
                    sharedViewModel.pkmn.value!!.sprites.front_default
                }
            }
        }

        Log.d(TAG, "getUrlAvatarFromGeneration: $url")
        if (url.isNullOrEmpty() || url == "null")
            url = sharedViewModel.pkmn.value!!.sprites.front_default.toString()
        Log.d(TAG, "getUrlAvatarFromGeneration after fix: $url")
        return url
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