package br.ufpr.mobile.trabalho2.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.ufpr.mobile.trabalho2.R

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "power_rangers.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "PRSeries"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY,
                nome TEXT,
                ano INTEGER,
                tema TEXT,
                integrantes INTEGER,
                antagonista TEXT,
                image TEXT
            );
        """.trimIndent()

        db.execSQL(createTable)

        val image1 = R.drawable.dino_thunder
        val image2 = R.drawable.spd

        val insertData = """
        INSERT INTO $TABLE_NAME (nome, ano, tema, integrantes, antagonista, image) 
            VALUES
            ('Power Rangers: Dino Thunder', 2003, 'Dinossauros', 5, 'Mesogog', '$image1'),
            ('Power Rangers: SPD', 2004, 'Policial/Futurista', 7, 'Imperador Gruumm', '$image2');
        """.trimIndent()

        db.execSQL(insertData)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}