package br.ufpr.mobile.trabalho2.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import br.ufpr.mobile.trabalho2.data.db.DBHelper
import br.ufpr.mobile.trabalho2.model.PRSeries

class PRSeriesDAO (private val context: Context)  {

    private val dbHelper = DBHelper(context)

    fun addPRSeries(prSeries: PRSeries): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", prSeries.nome)
            put("ano", prSeries.ano)
            put("tema", prSeries.tema)
            put("integrantes", prSeries.integrantes)
            put("antagonista", prSeries.antagonista)
            put("image", prSeries.image)
        }
        val id = db.insert("PRSeries", null, values)
        db.close()
        return id
    }

    fun getPRSeries(): List<PRSeries> {
        val db = dbHelper.readableDatabase
        val cursor : Cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null)
        val prSeriesList = mutableListOf<PRSeries>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val ano = cursor.getInt(cursor.getColumnIndexOrThrow("ano"))
            val tema = cursor.getString(cursor.getColumnIndexOrThrow("tema"))
            val integrantes = cursor.getInt(cursor.getColumnIndexOrThrow("integrantes"))
            val antagonista = cursor.getString(cursor.getColumnIndexOrThrow("antagonista"))
            val image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
            prSeriesList.add(PRSeries(id, nome, ano, tema, integrantes, antagonista, image))
        }

        cursor.close()
        db.close()
        return prSeriesList
    }

    fun getPRSeriesById(id: Int): PRSeries? {
        val db = dbHelper.readableDatabase
        val cursor : Cursor = db.query(DBHelper.TABLE_NAME, null, "id = ?", arrayOf(id.toString()), null, null, null)
        var prSeries: PRSeries? = null

        if (cursor.moveToFirst()) {
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val ano = cursor.getInt(cursor.getColumnIndexOrThrow("ano"))
            val tema = cursor.getString(cursor.getColumnIndexOrThrow("tema"))
            val integrantes = cursor.getInt(cursor.getColumnIndexOrThrow("integrantes"))
            val antagonista = cursor.getString(cursor.getColumnIndexOrThrow("antagonista"))
            val image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
            prSeries = PRSeries(id, nome, ano, tema, integrantes, antagonista, image)
        }
        cursor.close()
        db.close()
        return prSeries
    }

    fun updatePRSeries(prSeries: PRSeries): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", prSeries.nome)
            put("ano", prSeries.ano)
            put("tema", prSeries.tema)
            put("integrantes", prSeries.integrantes)
            put("antagonista", prSeries.antagonista)
            put("image", prSeries.image)
        }
        val rowsAffected = db.update(DBHelper.TABLE_NAME, values, "id = ?", arrayOf(prSeries.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deletePRSeries(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete(DBHelper.TABLE_NAME, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun searchPRSeries(query: String): List<PRSeries> {
        val db = dbHelper.readableDatabase;
        val cursor: Cursor = db.query(
            DBHelper.TABLE_NAME,
            null,
            "nome LIKE ? OR tema LIKE ? OR antagonista LIKE ?",
            arrayOf("%$query%", "%$query%", "%$query%"),
            null,
            null,
            null
        )
        val prSeriesList = mutableListOf<PRSeries>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val ano = cursor.getInt(cursor.getColumnIndexOrThrow("ano"))
            val tema = cursor.getString(cursor.getColumnIndexOrThrow("tema"))
            val integrantes = cursor.getInt(cursor.getColumnIndexOrThrow("integrantes"))
            val antagonista = cursor.getString(cursor.getColumnIndexOrThrow("antagonista"))
            val image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
            prSeriesList.add(PRSeries(id, nome, ano, tema, integrantes, antagonista, image))
        }
        cursor.close()
        db.close()
        return prSeriesList
    }
}