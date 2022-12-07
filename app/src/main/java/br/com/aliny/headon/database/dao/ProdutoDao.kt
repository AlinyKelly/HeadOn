package br.com.aliny.headon.database.dao

import androidx.room.*
import br.com.aliny.headon.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM Produto")
    fun buscaTodos(): Flow<List<Produto>>

    @Query("SELECT * FROM Produto WHERE usuarioId = :usuarioId")
    fun buscarTodosDoUsuario(usuarioId: String) : Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvar(vararg produto: Produto)

    @Delete
    suspend fun remover(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscarPorId(id: Long): Flow<Produto?>

}