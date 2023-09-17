package model

import kotlinx.serialization.Serializable

@Serializable
data class BirdImage(
    val author:String,
    val path:String,
    val category:String
)
