package br.com.aliny.headon.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import br.com.aliny.headon.R
import br.com.aliny.headon.database.AppDatabase
import br.com.aliny.headon.databinding.ActivityDetalhesProdutoBinding
import br.com.aliny.headon.extensions.formatarParaMoedaBrasileira
import br.com.aliny.headon.extensions.tentarCarregarImagem
import br.com.aliny.headon.model.Produto
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetalhesProdutoActivity : AppCompatActivity() {

    private var produtoId: Long = 0L
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentarCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscarProduto()
    }

    private fun buscarProduto() {
        lifecycleScope.launch {
            produtoDao.buscarPorId(produtoId).collect { produtoEncontrado ->
                produto = produtoEncontrado
                produto?.let {
                    preencherCampos(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                produto?.let {
                    lifecycleScope.launch {
                        produtoDao.remover(it)
                        finish()
                    }
                }

            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(CHAVE_PRODUTO_ID, produtoId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentarCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencherCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalhesProdutoImagem.tentarCarregarImagem(produtoCarregado.imagem)
            activityDetalhesProdutoNome.text = produtoCarregado.nome
            activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
            activityDetalhesProdutoValor.text =
                produtoCarregado.valor.formatarParaMoedaBrasileira()
        }
    }

}