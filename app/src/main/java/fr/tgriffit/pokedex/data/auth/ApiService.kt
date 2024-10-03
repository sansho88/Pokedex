package fr.tgriffit.pokedex.data.auth

import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Request {
    val POKEMONS = "pokemons"

    /**
     * Get an "array" of users matching perfectly the given name.
     * The array is size 1.
     * @param name=pokemon's name
     */
    fun pokemonByName(name: String): String {
        return "$POKEMONS?filter[login]=$name"
    }

    /**
     * Get the user with the given id.
     * @param id=id of pokemon searched
     */
    fun pokemonById(id: Int): String {
        return "$POKEMONS/$id"
    }
}

/**
 *
 */
class ApiService() {


    private var TAG = "ApiService"
    private val MAX_TIMEOUT = 42L
    private val requestPokeApiUrl = "https://pokeapi.co/api/v2/pokemon/"
    val request = Request()
    var lastResponseApi: ResponseApi? = null


    fun getAbout(info: String?): ResponseApi {
        var result = ResponseApi(code = 0, value = "")
        val executor = Executors.newSingleThreadExecutor() //for API calls!

        if (info.isNullOrEmpty())
            return ResponseApi(code = 404, value = "")
        executor.execute {
            result = callApi(info)
            executor.shutdown()
        }
        if (executor.awaitTermination(MAX_TIMEOUT, TimeUnit.SECONDS))
        {
            lastResponseApi = result
            return result
        }
        lastResponseApi = result
        return result
    }

    var nbTries = 0
    private fun callApi(endPoint: String): ResponseApi {
        val fullUrl = requestPokeApiUrl + endPoint


        try {
            val (_, response, result) = fullUrl.httpGet()
                .responseString()

            return when (result) {
                is Result.Success -> {
                    nbTries = 0
                    if (result.value == "[]")
                        ResponseApi(value = "")
                    else
                        ResponseApi(value = result.value)
                }

                is Result.Failure -> {
                    Log.e(
                        TAG, "[FAILURE] callApi: ${result.error.message}" +
                                "\n=>${response.responseMessage}"
                    )
                    ResponseApi(code = response.statusCode, value = response.responseMessage)
                }
            }
        } catch (exception: Exception) {
            Log.e(TAG, "callApi: $exception")
        }
        nbTries = 0
        return ResponseApi(code = -1, value = "Unknown error")
    }

    override fun toString(): String {
        return "ApiService(TAG='$TAG', requestApi42Url='$requestPokeApiUrl', request=$request)"
    }


    class ResponseApi(code: Int = 0, value: String) {
        var failure: Failure? = null
        var success: Success? = null

        class Failure(val code: Int, val message: String) {
        }

        class Success(val result: String) {
        }

        init {
            if (code == 0)
                success = Success(value)
            else
                failure = Failure(code, value)
        }
    }
}