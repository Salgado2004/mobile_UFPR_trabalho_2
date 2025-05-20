package br.ufpr.mobile.trabalho2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpr.mobile.trabalho2.adapter.RangerAdapter
import br.ufpr.mobile.trabalho2.data.dao.PRSeriesDAO

class MainActivity : AppCompatActivity() {

    private lateinit var rangerList: RecyclerView
    private lateinit var PRDao: PRSeriesDAO
    private lateinit var nomePesquisa: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        PRDao = PRSeriesDAO(this)
        rangerList = findViewById(R.id.powerRangerList)
        nomePesquisa = findViewById(R.id.pesquisaText)

        loadList()
        rangerList.layoutManager = LinearLayoutManager(this)
        rangerList.setHasFixedSize(true)
        rangerList.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))
    }

    fun loadList(){
        rangerList.adapter = RangerAdapter(PRDao.getPRSeries()){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }

    fun newPRSeries(view: View){
        val intent = Intent(this, FormActivity::class.java).apply {
            putExtra("id", 0)
        }
        startActivity(intent)
    }

    fun pesquisarPRSeries(view: View){
        val nome = nomePesquisa.text.toString()
        rangerList.adapter = RangerAdapter(PRDao.searchPRSeries(nome)){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }
}