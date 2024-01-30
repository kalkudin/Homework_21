package com.example.homework_21.data.common

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class HandleResponse @Inject constructor() {
    fun <T : Any> handleApiCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        try {
            Log.d("HandleResponse", "Starting API call")
            emit(Resource.Loading)
            val response = apiCall()
            if (response.isSuccessful) {
                Log.d("HandleResponse", "API call successful")
                emit(Resource.Success(response.body() ?: throw NullPointerException("Response body is null")))
            } else {
                Log.d("HandleResponse", "API call failed: Error Code: ${response.code()}")
                emit(Resource.Error("Error Code: ${response.code()}"))
            }
        } catch (e: IOException) {
            Log.e("HandleResponse", "Network error: ${e.localizedMessage}", e)
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            Log.e("HandleResponse", "Unknown error: ${e.localizedMessage}", e)
            emit(Resource.Error("Unknown error: ${e.localizedMessage}"))
        }
    }
}