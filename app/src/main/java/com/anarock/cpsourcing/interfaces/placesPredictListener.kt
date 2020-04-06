package com.anarock.cpsourcing.interfaces

import com.anarock.cpsourcing.model.PlacesPredict

interface placesPredictListener {
    fun onResponse(placesPredict: PlacesPredict)
}