package com.sample.arrive.network

import com.squareup.moshi.Json

// APIレスポンス
data class HeartRailsExpressProperty<T> (
    val response: T)

//エリアリスト
data class AreaResponse (
    val area: List<String>)

//都道府県リスト
data class PrefecturesResponse (
    val prefecture: List<String>)

//路線リスト
data class LinesResponse (
    val line: List<String>)

//駅リスト
data class StationsResponse (
    val station: List<Station>
)

//駅情報
data class Station (
    val name: String,
    val line: String = "",
    @Json(name="x") val longitude:Double = 0.0,
    @Json(name="y") val latitude:Double = 0.0
)