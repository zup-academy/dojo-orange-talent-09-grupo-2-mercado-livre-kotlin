package br.com.zup.edu.mercadolivrekotlin.autores

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class NovoUsuarioControllerTest(
) {
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var mockMvc: MockMvc

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
}