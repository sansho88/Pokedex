package fr.tgriffit.pokedex.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import fr.tgriffit.pokedex.data.DescriptionPokemon
import fr.tgriffit.pokedex.data.Pokemon
import fr.tgriffit.pokedex.data.auth.ApiService
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class PkmnSharedViewModel : ViewModel() {

    private val gson = Gson()
    private val _searchQuery = MutableLiveData<String>()
    private val _result = MutableLiveData<String?>()
    private val _pkmn = MutableLiveData<Pokemon>()
    private val _descPkmn = MutableLiveData<DescriptionPokemon>()
    private val _apiService = MutableLiveData<ApiService>()
    private val _index = MutableLiveData<Int>()
    private val _version = MutableLiveData<Version>()
    private val _stats = MutableLiveData<List<Stat>>()
    private val _skillsList = MutableLiveData<List<Move>>()
    val searchQuery: LiveData<String> = _searchQuery
    val pkmn: LiveData<Pokemon?> = _pkmn
    val descPkmn: LiveData<DescriptionPokemon?> = _descPkmn
    val result: LiveData<String?> = _result
    val apiService: LiveData<ApiService> = _apiService
    val index: LiveData<Int> = _index
    val version: LiveData<Version> = _version
    val stats: LiveData<List<Stat>> = _stats
    val skillsList: LiveData<List<Move>> = _skillsList

    fun setSearchQuery(query: String): PkmnSharedViewModel {
        _searchQuery.postValue(query)
        _searchQuery.value = query
        return this
    }

    fun setResult(result: String?): PkmnSharedViewModel {
        _result.postValue(result)
        _result.value = result
        return this
    }

    fun setPkmn(pkmn: Pokemon): PkmnSharedViewModel {
        _pkmn.postValue(pkmn)
        _pkmn.value = pkmn
        return this
    }

    fun setDescPkmn(descPkmn: DescriptionPokemon): PkmnSharedViewModel {
        _descPkmn.postValue(descPkmn)
        _descPkmn.value = descPkmn
        return this
    }

    fun setApiService(apiService: ApiService): PkmnSharedViewModel {
        _apiService.postValue(apiService)
        _apiService.value = apiService
        return this
    }

    fun setIndex(index: Int) {
        _index.postValue(index)
    }

    fun setVersion(version: Version): PkmnSharedViewModel {
        _version.value = version
        return this
    }

    fun setStats(stats: List<Stat>): PkmnSharedViewModel {
        _stats.value = stats
        return this
    }

    fun setSkillsList(skillsList: List<Move>): PkmnSharedViewModel {
        _skillsList.value = skillsList
        return this
    }

    /**
     * Get the pkmn from the result.
     * @return the pkmn or null if the result variable is null or empty
     * @see performSearch
     */
    fun getPkmnFromResult(): Pokemon? {
        if (!result.isInitialized || result.value.isNullOrEmpty()) {
            Log.e("SharedViewModel", "getPokemonFromResult: result is null or empty")
            return null
        }
        try {
            return pkmn.value
        } catch (e: JsonSyntaxException) {
            Log.e(
                "SharedViewModel", "getPokemonFromResult: " +
                        "The result can't be converted into Pokemon object"
            )
            return null
        }
    }

    /**
     * Perform a search on the API.
     * The result is stored in the result variable.
     */
    private fun performSearch(): ApiService.ResponseApi? {
        if (!apiService.isInitialized) {
            Log.e("SharedViewModel", "performSearch: apiService is null")
            return ApiService.ResponseApi(-1, "apiService is null")
        }
        var apiResult: ApiService.ResponseApi? = ApiService.ResponseApi(-1, "Result not get yet")
        val search = viewModelScope.launch {
            apiResult = apiService.value?.getAbout(searchQuery.value)
            if (apiResult != null && apiResult!!.success != null)
                setResult(apiResult!!.success!!.result)
            else
                setResult(null)
        }
        when (search.isCompleted) {
            true -> Log.d("SharedViewModel", "performSearch: search is completed")
            false -> Log.d("SharedViewModel", "performSearch: search is not completed")
        }
        return apiResult
    }

    private fun searchEntity(request: String): String? {
        this.setSearchQuery(request)
        this.performSearch()
        return result.value
    }

    fun searchPokemon(name: String): Pokemon? {
        val pkmnsResult = searchEntity(apiService.value!!.request.pokemonByName(name))
        if (pkmnsResult.isNullOrEmpty())
            return null
        return convertResponseToPokemon(apiService.value!!.lastResponseApi!!)
    }

    fun getRandomPokemon(): Pokemon? {
        val rndResult = searchEntity(apiService.value!!.request.getRndmPkmn())
        if (rndResult.isNullOrEmpty())
            return null
        val convertedResult = convertResponseToPokemon(apiService.value!!.lastResponseApi!!)
        return convertedResult
    }

    fun updatePkmn(pkmn: Pokemon?) {
        if (pkmn == null) {
            Log.e("SharedViewModel", "updatePkmn: pokemon not found")
            return
        }
        setPkmn(pkmn)
        setStats(pkmn.stats)
        setSkillsList(pkmn.moves)
        val desc = getPkmnDesc(pkmn)
        if (desc != null)
            setDescPkmn(desc)
    }

    private fun convertResponseToPokemon(response: ApiService.ResponseApi): Pokemon? {
        var pkmn: Pokemon? = null
        if (response.success == null)
            return null

        try {
            pkmn = gson.fromJson(response.success!!.result, Pokemon::class.java)
            Log.d("SharedViewModel", "convertResponseToPokemon: $pkmn")
        } catch (e: Exception) {
            Log.e("SharedViewModel", "CONVERT TO GSON FAILED")
            Log.e("SharedViewModel", "searchPokemon: ${e.message}")
            Log.e("SharedViewModel", "result string: ${response.failure?.code}")
        }
        return pkmn
    }

    fun getPkmnDesc(pokemon: Pokemon?): DescriptionPokemon? {
        if (pokemon == null)
            return null
        val descResult = searchEntity(apiService.value!!.request.descPkmnFromPkdx(pokemon.id))
        if (descResult.isNullOrEmpty())
            return null
        var desc: DescriptionPokemon? = null
        try {
            desc = gson.fromJson(descResult, DescriptionPokemon::class.java)
        } catch (e: Exception) {
            Log.e("SharedViewModel", "CONVERT TO GSON FAILED")
        }
        return desc
    }
}
