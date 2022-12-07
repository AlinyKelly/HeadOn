package br.com.aliny.headon.extensions

import android.widget.ImageView
import br.com.aliny.headon.R

import coil.load

fun ImageView.tentarCarregarImagem(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
){
    load(url) {
        fallback(fallback)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}