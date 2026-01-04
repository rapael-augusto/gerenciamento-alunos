package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@email.com");
        usuario.setSenha("senha");
        usuario.setUser("admin");
        usuarioRepository.save(usuario);
    }

    @Test
    public void testFindByEmail(){
        Usuario resultado = usuarioRepository.findByEmail("teste@email.com");

        assertNotNull(resultado, "Não pode ser null!");
        assertEquals("teste@email.com", resultado.getEmail());
        assertEquals("admin", resultado.getUser());
    }

    @Test
    public void testFindByEmail_ExceptionNonExistentEmail(){
        Usuario resultado = usuarioRepository.findByEmail("inexistente@gmail.com");

        assertNull(resultado, "nulo");
    }

    @Test
    public void testBuscarLogin(){
        Usuario resultado = usuarioRepository.buscarLogin("admin", "senha");

        assertNotNull(resultado, "Não pode ser null!");
        assertEquals("admin", resultado.getUser());
        assertEquals("senha", resultado.getSenha());
    }

    @Test
    public void testBuscarLogin_ExceptionNonExistentLogin(){
        Usuario resultado = usuarioRepository.buscarLogin("inexistente", "errada");

        assertNull(resultado, "nulo");
    }

}