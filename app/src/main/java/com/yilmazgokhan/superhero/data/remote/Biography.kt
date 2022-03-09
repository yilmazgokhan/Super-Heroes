package com.yilmazgokhan.superhero.data.remote

data class Biography(
    var fullName: String? = null,
    var alterEgos: String? = null,
    var aliases: List<String>? = null,
    var placeOfBirth: String? = null,
    var firstAppearance: String? = null,
    var publisher: String? = null,
    var alignment: String? = null
)