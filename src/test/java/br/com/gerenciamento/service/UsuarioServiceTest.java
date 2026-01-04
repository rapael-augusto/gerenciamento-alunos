package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ServiceUsuario serviceUsuario;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("Vinicius");
        usuario.setEmail("usuario@gmail.com");
        usuario.setSenha("123456");
    }

    @Test
    public void testSalvarUsuario() throws Exception {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(null);

        serviceUsuario.salvarUsuario(usuario);

        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testSalvarUsuario_ExceptionEmailExists() {
        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(usuario);

        EmailExistsException exception = assertThrows(EmailExistsException.class, () -> serviceUsuario.salvarUsuario(usuario));

        assertTrue(exception.getMessage().contains("usuario@gmail.com"));
    }

    @Test
    public void testLoginUser(){
        when(usuarioRepository.buscarLogin(usuario.getUser(), usuario.getSenha())).thenReturn(usuario);

        serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());

        verify(usuarioRepository, times(1)).buscarLogin(usuario.getUser(), usuario.getSenha());
    }

    @Test
    public void testLoginUser_ExceptionNonExistentUser() {
        when(usuarioRepository.buscarLogin("inexistente","123")).thenReturn(null);

        Usuario inexistente = serviceUsuario.loginUser("inexistente", "123");

        assertNull(inexistente);
        verify(usuarioRepository).buscarLogin("inexistente","123");
    }

}
