package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class PlatformXXXXXXX(
    @SerializedName("platform")
    val platform: PlatformXXXXXXXX,
    @SerializedName("released_at")
    val releasedAt: String,
    @SerializedName("requirements_en")
    val requirementsEn: RequirementsEn,
    @SerializedName("requirements_ru")
    val requirementsRu: RequirementsRu
)