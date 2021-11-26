package br.com.zup.edu.mercadolivrekotlin.autores

import org.springframework.security.crypto.bcrypt.BCrypt
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class NovoUsuarioRequest(
    @field:NotBlank @field:Email val login: String,
    @field:NotBlank @field:Size(min = 6) val senha: String,
) {
    fun paraUsuario(): Usuario {
        return Usuario(login, BCrypt.hashpw(senha, BCrypt.gensalt()))
    }

}
