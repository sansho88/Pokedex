package fr.tgriffit.pokedex

import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.allViews
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import fr.tgriffit.pokedex.data.DescriptionPokemon
import fr.tgriffit.pokedex.data.Pokemon
import fr.tgriffit.pokedex.data.auth.ApiService
import fr.tgriffit.pokedex.data.model.FlavorTextEntry
import fr.tgriffit.pokedex.data.model.PkmnSharedViewModel
import fr.tgriffit.pokedex.databinding.ActivityHomeBinding
import fr.tgriffit.pokedex.ui.main.SkillsFragment
import fr.tgriffit.pokedex.ui.main.SectionsPagerAdapter
import fr.tgriffit.pokedex.ui.main.StatsFragment
import fr.tgriffit.pokedex.ui.main.PokemonProfileFragment


class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    val MAX_LOGIN_LEN = 12

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var searchView: SearchView
    private lateinit var versionSelector: Spinner
    private lateinit var rndPkmnButton: ImageButton
    private var pkmn: Pokemon? = null
    private var descPkmn: DescriptionPokemon? = null
    private lateinit var apiService: ApiService

    //todo: Update SHaredViewModel with PokemonData updated
    private val sharedViewModel: PkmnSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.addFragment(PokemonProfileFragment())
        sectionsPagerAdapter.addFragment(SkillsFragment())
        sectionsPagerAdapter.addFragment(StatsFragment())

        viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        tabs = binding.tabs
        tabs.setupWithViewPager(viewPager)
        tabs.setSelectedTabIndicatorColor(resources.getColor(R.color.app_theme_tertiary_color, theme))



        versionSelector = binding.spinner
        versionSelector.textAlignment = View.TEXT_ALIGNMENT_CENTER
        versionSelector.gravity = View.TEXT_ALIGNMENT_CENTER
        //versionSelector.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        rndPkmnButton = binding.meButton

        apiService = ApiService()
        sharedViewModel.setApiService(apiService)
        sharedViewModel.apiService.observe(this, apiServiceObserver())


        rndPkmnButton.setOnClickListener {
            Toast.makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show()
            val rnd = sharedViewModel.getRandomPokemon()
            if (rnd == null)
                Toast.makeText(this, "Timeout\nPlease, check your connexion", Toast.LENGTH_LONG)
                    .show()
            else{
                sharedViewModel.updatePkmn(rnd)
                changeVersion(sharedViewModel.descPkmn.value!!.flavor_text_entries)
            }
        }

        searchView = binding.searchUserSearchView
        var lastSearched = ""
        val searchEditText = searchView.allViews.find { view -> view is EditText } as EditText
        searchEditText.filters = arrayOf<InputFilter>(LengthFilter(MAX_LOGIN_LEN))
        searchEditText.textSize = 22f

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty() && lastSearched != query) {

                    lastSearched = query
                    pkmn = sharedViewModel.searchPokemon(query)
                    if (pkmn == null) {
                        Toast.makeText(
                            this@HomeActivity,
                            "$query doesn't exist",
                            Toast.LENGTH_SHORT
                        ).show()
                        searchEditText.setTextColor(Color.RED)
                        Log.d(
                            "HomeActivity",
                            "onQueryTextSubmit: ${sharedViewModel.apiService.value?.lastResponseApi?.failure?.message}"
                        )
                        return false
                    }
                    sharedViewModel.updatePkmn(pkmn)
                    descPkmn = sharedViewModel.getPkmnDesc(pkmn)
                    if (descPkmn != null){
                        sharedViewModel.setDescPkmn(descPkmn!!)
                        changeVersion(sharedViewModel.descPkmn.value!!.flavor_text_entries)
                    }

                    searchEditText.text.clear()
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) return false
                if (searchEditText.currentTextColor == Color.RED)
                    searchEditText.setTextColor(Color.WHITE)

                if (newText.length > MAX_LOGIN_LEN)
                    Toast.makeText(
                        this@HomeActivity,
                        "A pokemon's name can't be bigger",
                        Toast.LENGTH_SHORT
                    ).show()

                return true
            }
        })

        val rndPkmn = sharedViewModel.getRandomPokemon()
        if (rndPkmn == null)
            Toast.makeText(this, "Timeout\nPlease, check your connexion", Toast.LENGTH_LONG)
                .show()
        else{
            if (sharedViewModel.descPkmn.value != null)
                changeVersion(sharedViewModel.descPkmn.value!!.flavor_text_entries)
        }
        sharedViewModel.updatePkmn(rndPkmn)

        tabs.isClickable = false
        versionSelector.isClickable = false

    }

    private fun apiServiceObserver(): Observer<ApiService> {
        val observer = Observer<ApiService> {
            try {
                var tmpPokemon = sharedViewModel.getPkmnFromResult() ?: sharedViewModel.getRandomPokemon()
                if (tmpPokemon != null) {
                    tabs.isClickable = true
                    versionSelector.isClickable = true
                    pkmn = tmpPokemon
                    descPkmn = sharedViewModel.getPkmnDesc(pkmn!!)
                  changeVersion(descPkmn!!.flavor_text_entries)
                      sharedViewModel.updatePkmn(pkmn)
                } else {
                    Log.e("HomeActivity", "onCreate: tmpPokemon is null")
                    tmpPokemon = sharedViewModel.getRandomPokemon()
                    sharedViewModel.updatePkmn(tmpPokemon)
                    pkmn = tmpPokemon
                }

            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ApiService().getMe: ", e)
            }
        }
        return observer
    }

    private fun changeVersion(descList: List<FlavorTextEntry>) {
        val filteredList = descList.filter { it.language.name == "en" }
        val namesList = filteredList.map { it.version.name.replace("-", " ") }

        val adapter = ArrayAdapter(
            this,
            R.layout.cursus_spinner_item,
            namesList

        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        versionSelector.adapter = adapter


        versionSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sharedViewModel.setVersion(filteredList[position].version)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                sharedViewModel.setVersion(descList[0].version)
            }
        }
    }

}