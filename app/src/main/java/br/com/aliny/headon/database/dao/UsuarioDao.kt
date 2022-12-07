package br.com.aliny.headon.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.aliny.headon.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun salvar(usuario: Usuario)

    @Query("""
        SELECT * FROM Usuario 
        WHERE id = :usuarioId 
        AND senha = :senha""")
    suspend fun autentica(
        usuarioId: String,
        senha: String
    ): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :usuarioId")
    fun buscaPorId(usuarioId: String): Flow<Usuario>

}