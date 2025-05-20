package br.ufpr.mobile.trabalho2

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.mobile.trabalho2.data.dao.PRSeriesDAO
import br.ufpr.mobile.trabalho2.model.PRSeries

class FormActivity : AppCompatActivity() {

    var seriesId = 0
    lateinit var prSeriesDAO: PRSeriesDAO
    lateinit var prNameEditText: EditText
    lateinit var prAnoEditText: EditText
    lateinit var prIntegrantesEditText: EditText
    lateinit var prTemaEditText: EditText
    lateinit var prVilaoEditText: EditText

    val images = listOf(
        R.drawable.mighty_morphin,
        R.drawable.mighty_morphin_alien_force,
        R.drawable.zeo,
        R.drawable.turbo,
        R.drawable.space,
        R.drawable.lost_galaxy,
        R.drawable.lightspeed_rescue,
        R.drawable.time_force,
        R.drawable.wild_force,
        R.drawable.ninja_storm,
        R.drawable.dino_thunder,
        R.drawable.spd,
        R.drawable.mystic_force,
        R.drawable.operation_overdrive,
        R.drawable.jungle_fury,
        R.drawable.rpm,
        R.drawable.samurai,
        R.drawable.megaforce,
        R.drawable.dino_supercharge,
        R.drawable.dino_fury
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prSeriesDAO = PRSeriesDAO(this)

        prNameEditText = findViewById(R.id.prNameEditText)
        prAnoEditText = findViewById(R.id.prAnoEditText)
        prIntegrantesEditText = findViewById(R.id.prIntegrantesEditText)
        prTemaEditText = findViewById(R.id.prTemaEditText)
        prVilaoEditText = findViewById(R.id.prVilaoEditText)

        seriesId = intent.getIntExtra("id", 0, )
        if (seriesId != 0)
            loadPRSeries()
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    fun loadPRSeries() {
        prSeriesDAO.getPRSeriesById(seriesId)?.let { series ->
            prNameEditText.text = series.nome.toEditable()
            prAnoEditText.text = series.ano.toString().toEditable()
            prTemaEditText.text = series.tema.toEditable()
            prIntegrantesEditText.text = series.integrantes.toString().toEditable()
            prVilaoEditText.text = series.antagonista.toEditable()
        }
    }

    fun cancelar(view: View){
        finish()
    }

    fun salvar(view: View){
        if (
            prNameEditText.text.isNotEmpty() &&
            prAnoEditText.text.isNotEmpty() &&
            prTemaEditText.text.isNotEmpty() &&
            prIntegrantesEditText.text.isNotEmpty() &&
            prVilaoEditText.text.isNotEmpty()
        ){
            if (seriesId == 0){
                val newPRSeries = PRSeries(
                    nome = prNameEditText.text.toString(),
                    ano = prAnoEditText.text.toString().toInt(),
                    tema = prTemaEditText.text.toString(),
                    integrantes = prIntegrantesEditText.text.toString().toInt(),
                    antagonista = prVilaoEditText.text.toString(),
                    image = images.shuffled()[0].toString()
                )
                prSeriesDAO.addPRSeries(newPRSeries)
                Toast.makeText(this, "Série de Power Rangers adicionada", Toast.LENGTH_SHORT).show()
            } else {
                val updatedSeries = PRSeries(
                    id = seriesId,
                    nome = prNameEditText.text.toString(),
                    ano = prAnoEditText.text.toString().toInt(),
                    tema = prTemaEditText.text.toString(),
                    integrantes = prIntegrantesEditText.text.toString().toInt(),
                    antagonista = prVilaoEditText.text.toString(),
                    image = images.shuffled()[0].toString()
                )
                prSeriesDAO.updatePRSeries(updatedSeries)
                Toast.makeText(this, "Série de Power Rangers atualizada", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }
}