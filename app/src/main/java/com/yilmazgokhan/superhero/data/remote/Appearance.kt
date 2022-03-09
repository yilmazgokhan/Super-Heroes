package com.yilmazgokhan.superhero.data.remote

data class Appearance(
    var gender: String? = null,
    var race: String? = null,
    var height: List<String>? = null,
    var weight: List<String>? = null,
    var eyeColor: String? = null,
    var hairColor: String? = null
)