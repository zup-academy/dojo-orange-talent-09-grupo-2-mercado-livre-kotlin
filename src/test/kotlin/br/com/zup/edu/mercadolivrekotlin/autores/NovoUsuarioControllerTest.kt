package br.com.zup.edu.mercadolivrekotlin.autores

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
internal class NovoUsuarioControllerTest(
    val mockMvc: MockMvc,
    val usuarioRepository: UsuarioRepository,
) {
    @Test
    fun `Deve salvar um usuario com sucesso`() {
        val usuario = Usuario("email@email.com", "123456")
    }

}