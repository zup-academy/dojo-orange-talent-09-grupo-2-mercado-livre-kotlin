package br.com.zup.edu.mercadolivrekotlin.autores

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
class Usuario(
    @field:NotBlank @field:Email @Column(nullable = false) val login: String,
    @field:NotBlank @field:Size(min = 6) @Column(nullable = false) val senha: String,
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    val instante: LocalDateTime = LocalDateTime.now()
}
