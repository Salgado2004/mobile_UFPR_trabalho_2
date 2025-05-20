package br.ufpr.mobile.trabalho2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.mobile.trabalho2.data.dao.PRSeriesDAO

class DetailsActivity : AppCompatActivity() {

    var seriesId = 0
    lateinit var prSeriesDAO: PRSeriesDAO
    lateinit var prNameView: TextView
    lateinit var prAnoView: TextView
    lateinit var prIntegrantesView: TextView
    lateinit var prTemaView: TextView
    lateinit var prVilaoView: TextView
    lateinit var prImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prSeriesDAO = PRSeriesDAO(this)

        prNameView = findViewById(R.id.prNameView)
        prAnoView = findViewById(R.id.prAnoView)
        prIntegrantesView = findViewById(R.id.prIntegrantesView)
        prTemaView = findViewById(R.id.prTemasView)
        prVilaoView = findViewById(R.id.prVilaoView)
        prImageView = findViewById(R.id.prImageView)

        seriesId = intent.getIntExtra("id", 0, )
        if (seriesId != 0) {
            loadPRSeries()
        } else {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadPRSeries()
    }

    fun loadPRSeries() {
        prSeriesDAO.getPRSeriesById(seriesId)?.let { series ->
            prNameView.text = series.nome
            prAnoView.text = series.ano.toString()
            prTemaView.text = series.tema
            prIntegrantesView.text = series.integrantes.toString()
            prVilaoView.text = series.antagonista
            prImageView.setImageResource(series.image.toInt())
        }
    }

    fun deletePRSeries(view: View){
        prSeriesDAO.deletePRSeries(seriesId)
        Toast.makeText(this, "Série de Power Rangers deletada", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun editPRSeries(view: View){
        val intent = Intent(this, FormActivity::class.java).apply {
            putExtra("id", seriesId)
        }
        startActivity(intent)
    }
}