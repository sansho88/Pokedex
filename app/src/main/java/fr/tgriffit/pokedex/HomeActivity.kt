package fr.tgriffit.pokedex

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.Log
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
import fr.tgriffit.pokedex.data.Pokemon
import fr.tgriffit.pokedex.data.auth.ApiService
import fr.tgriffit.pokedex.data.model.PkmnSharedViewModel
import fr.tgriffit.pokedex.databinding.ActivityHomeBinding
import fr.tgriffit.pokedex.ui.main.ProjectFragment
import fr.tgriffit.pokedex.ui.main.SectionsPagerAdapter
import fr.tgriffit.pokedex.ui.main.SkillsFragment
import fr.tgriffit.pokedex.ui.main.PokemonProfileFragment


class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    val MAX_LOGIN_LEN = 12

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout
    private lateinit var searchView: SearchView
    private lateinit var cursusSpinner: Spinner
    private lateinit var meButton: ImageButton
    private lateinit var logoutButton: ImageButton
    private var pkmn: Pokemon? = null
    private lateinit var apiService: ApiService
    //todo: Update SHaredViewModel with PokemonData updated
    private val sharedViewModel: PkmnSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.addFragment(PokemonProfileFragment())
        sectionsPagerAdapter.addFragment(ProjectFragment())
        sectionsPagerAdapter.addFragment(SkillsFragment())

        viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        tabs = binding.tabs
        tabs.setupWithViewPager(viewPager)
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#2561B4"))


        cursusSpinner = binding.spinner
        cursusSpinner.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
        meButton = binding.meButton
        logoutButton = binding.logoutButton


        apiService = ApiService()
        sharedViewModel.setApiService(apiService)
        sharedViewModel.apiService.observe(this, apiServiceObserver())
        meButton.setOnClickListener {
            Toast.makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show()
            val responseApi = sharedViewModel.apiService.value?.getAbout("me")
            if (responseApi?.success != null)
            {
                sharedViewModel.setResult(responseApi.success!!.result)
                val me = sharedViewModel.getPkmnFromResult()
                if (me == null)
                    Toast.makeText(this, "Timeout\nPlease, check your connexion", Toast.LENGTH_LONG).show()
                else{
                    searchView.setQuery(me.name, true)
                    searchView.clearFocus()
                    searchView.setQuery("", false)
                }
            }
        }
        logoutButton.setOnClickListener {

            finish()
        }

        searchView = binding.searchUserSearchView
        var lastSearched = ""
        val searchEditText = searchView.allViews.find { view -> view is EditText } as EditText
        //set maxLength of login
        searchEditText.filters = arrayOf<InputFilter>(LengthFilter(MAX_LOGIN_LEN))
        searchEditText.textSize = 22f

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty() && lastSearched != query) {
                    Toast.makeText(this@HomeActivity, "Fetching data...", Toast.LENGTH_SHORT).show()

                    lastSearched = query
                    pkmn = sharedViewModel.searchPokemon(query)
                    if (pkmn == null) {
                        Toast.makeText(
                            this@HomeActivity,
                            "$query doesn't exist",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("HomeActivity", "onQueryTextSubmit: ${sharedViewModel.apiService.value?.lastResponseApi?.failure?.message}")
                        return false
                    }
                    sharedViewModel.setPkmn(pkmn!!)
                    Log.d("HomeActivity", "pkmn variable= ${sharedViewModel.pkmn.value}")
                    //changeProjectsList(pkmn!!.cursus_pkmns)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) return false

                if ( newText.length > MAX_LOGIN_LEN)
                    Toast.makeText(
                        this@HomeActivity,
                        "A login can't be bigger",
                        Toast.LENGTH_SHORT
                    ).show()

                return true
            }
        })

    }

    private fun apiServiceObserver(): Observer<ApiService> {
        val observer = Observer<ApiService> {
            try {
               /* val me = sharedViewModel.apiService.value?.getAbout("me")
                if (me?.success != null)
                    sharedViewModel.setResult(me.success!!.result)
                else {
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()

                    finish()
                }*/
                val tmpPokemon = sharedViewModel.getPkmnFromResult()
                if (tmpPokemon != null) {
                    sharedViewModel.setPkmn(tmpPokemon)
                    pkmn = tmpPokemon
                    //changeProjectsList(sharedViewModel.pkmn.value!!.cursus_pkmns)
                } else
                {
                    Log.e("HomeActivity", "onCreate: tmpPokemon is null")
                }

            } catch (e: Exception) {
                Log.e(TAG, "onCreate: ApiService().getMe: ", e)
            }
        }
        return observer
    }

    /*private fun changeProjectsList(cursusPokemonList: List<PokemonData.CursusPokemon>) {
        val adapter = ArrayAdapter(
            this,
            R.layout.cursus_spinner_item,
            cursusPokemonList.map { it.cursus.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cursusSpinner.adapter = adapter


        cursusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sharedViewModel.setCurrentCursus(cursusPokemonList[position])
                sharedViewModel.setProjectsList(
                    sharedViewModel.pkmn.value!!.getProjectsPokemons().filter { project ->
                        project.cursus_ids.find { id ->
                            id == cursusPokemonList[position].cursus_id
                        } != null
                    })
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }*/

}