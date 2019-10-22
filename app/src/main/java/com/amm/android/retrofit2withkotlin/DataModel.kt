package com.amm.android.retrofit2withkotlin

object DataModel {
    data class Result(val query:Query)
    data class Query(val searchinfo:SearchInfo)
    data class SearchInfo(val totalhits:Int)
}