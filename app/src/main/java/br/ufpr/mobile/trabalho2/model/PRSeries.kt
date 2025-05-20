package br.ufpr.mobile.trabalho2.model

data class PRSeries(
    val id: Int = 0,
    val nome: String,
    val ano: Int,
    val tema: String,
    val integrantes: Int,
    val antagonista: String,
    val image: String
)