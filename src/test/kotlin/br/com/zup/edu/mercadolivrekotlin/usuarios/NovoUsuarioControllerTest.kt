package br.com.zup.edu.mercadolivrekotlin.usuarios

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
internal class NovoUsuarioControllerTest(
) {
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Test
    fun `Deve salvar um usuario com sucesso`() {
        val usuarioRequest = NovoUsuarioRequest("teste@email.com.br", "123456");

        val content = objectMapper.writeValueAsString(usuarioRequest);

        val request = MockMvcRequestBuilders.post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())

    }

    @ParameterizedTest
    @CsvSource(
        "email@teste.com, 12345",
        "joão, 123456"
    )
    fun `Nao deve salvar usuario com dados invalidos`(login: String, senha: String) {

        val usuarioRequest = NovoUsuarioRequest(login, senha)
        val content = objectMapper.writeValueAsString(usuarioRequest)

        val request = MockMvcRequestBuilders.post("/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

        mockMvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())

    }

    @Test
    @Transactional
    fun `Não deve salvar um usuario com login repetido`() {
        usuarioRepository.save(Usuario("teste@email.com.br", "123456"))

        val usuarioRequest = NovoUsuarioRequest("teste@email.com.br", "123456");

        val content = objectMapper.writeValueAsString(usuarioRequest);

        val request = MockMvcRequestBuilders.post("/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

        mockMvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())

    }
}