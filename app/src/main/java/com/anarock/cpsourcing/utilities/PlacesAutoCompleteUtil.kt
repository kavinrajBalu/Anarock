package com.anarock.cpsourcing.utilities

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.interfaces.placesPredictListener
import com.anarock.cpsourcing.model.PlacesPredict
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest


class PlacesAutoCompleteUtil  {
    companion object {
        private const val TAG = "PlacesAutoCompleteUtil"
        fun findAutoCompletePredictions(query: String, context: Context, placesPredictListener: placesPredictListener){
            val token: AutocompleteSessionToken = AutocompleteSessionToken.newInstance()


// Create a new Places client instance
            val placesClient = Places.createClient(context)
            val request: FindAutocompletePredictionsRequest =
                FindAutocompletePredictionsRequest.builder() // Call either setLocationBias() OR setLocationRestriction().
//                    .setOrigin(LatLng(-33.8749937, 151.2041382))
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .setSessionToken(token)
                    .setQuery(query)
                    .build();

//            var placesPredictLiveData: MutableLiveData<PlacesPredict> = MutableLiveData()
            var placesPredict = PlacesPredict()
            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                for (prediction in response.autocompletePredictions) {
                    Log.i(TAG, prediction.placeId)
                    Log.i(TAG, prediction.getPrimaryText(null).toString())
                    placesPredict.placeId = prediction.placeId
                    placesPredict.placePrimary = prediction.getPrimaryText(null).toString()
//                    placesPredictLiveData.value = placesPredict
                    placesPredictListener.onResponse(placesPredict)
                }
            }.addOnFailureListener { exception ->
                if (exception is ApiException) {
                    Log.e(TAG, "Place not found: " + exception.statusCode)
                }
            }
        }
    }
}