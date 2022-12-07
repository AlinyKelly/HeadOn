package br.com.aliny.headon.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.aliny.headon.databinding.FormularioImagemBinding
import br.com.aliny.headon.extensions.tentarCarregarImagem

class FormularioImagemDialog(private val context: Context) {

    fun exibir(
        urlPadrao: String? = null,
        quandoImagemCarragada: (imagem: String) -> Unit
    )  {
        FormularioImagemBinding
            .inflate(LayoutInflater.from(context)).apply {

                urlPadrao?.let {
                    formularioImagemImageview.tentarCarregarImagem(it)
                    formularioImagemUrl.setText(it)
                }

                formularioImagemBotaoCarregar.setOnClickListener {
                    val url = formularioImagemUrl.text.toString()
                    formularioImagemImageview.tentarCarregarImagem(url)
                }

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formularioImagemUrl.text.toString()
                        quandoImagemCarragada(url)
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }




    }

}