package com.example.atividade2json

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var textResultado: TextView
    private lateinit var btnEnviarDados: Button
    private lateinit var btnRecuperarDados: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Inicializa elementos da interface
        editNome = findViewById(R.id.edit_nome)
        editEmail = findViewById(R.id.edit_email)
        editSenha = findViewById(R.id.edit_senha)
        textResultado = findViewById(R.id.text_resultado)
        btnEnviarDados = findViewById(R.id.btn_enviar_dados)
        btnRecuperarDados = findViewById(R.id.btn_recuperar_dados)

        //Botão que envia e salva os dados
        btnEnviarDados.setOnClickListener {
            val nome = editNome.text.toString()
            val email = editEmail.text.toString()
            val senha = editSenha.text.toString()
            val data = DataModel(nome, email, senha)
            saveData(this, data)
        }

        //Botão que recupera e exibe os dados
        btnRecuperarDados.setOnClickListener {
            val data = loadData(this)
            data?.let{
                val resultText = "Nome: ${it.nome} \nEmail: ${it.email} \nSenha: ${it.senha}"
                textResultado.text = resultText
            }
        }
    }

    //função que converte o dataModel para Json e salvar no arquivo dados.json
    fun saveData(context: Context, data: DataModel) {
        val gson = Gson()
        val jsonData = gson.toJson(data)
        val file = File(context.filesDir, "dados.json")
        file.writeText(jsonData)
    }

    //função que lê o arquivo dados.json e retornar um dataModel
    fun loadData(context: Context): DataModel?{
        val file = File(context.filesDir, "dados.json")
        if(file.exists()){
            val jsonData = file.readText()
            val gson = Gson()
            return gson.fromJson(jsonData, DataModel::class.java)
        }
        return null
    }
}