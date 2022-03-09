package com.yilmazgokhan.superhero.data.remote

data class ResponseHero(
    var id: String? = null,
    var name: String? = null,
    var slug: String? = null,
    var powerstats: PowerStats? = null,
    var appearance: Appearance? = null,
    var biography: Biography? = null,
    var work: Work? = null,
    var connections: Connections? = null,
    var images: Images? = null
)