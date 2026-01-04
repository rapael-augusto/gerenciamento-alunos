package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private MockHttpSession session;

    @BeforeEach
    public void setUp(){
        usuarioRepository.deleteAll();

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@gmail.com");
        usuario.setSenha("123456");
        usuario.setUser("userTeste");
        session = new MockHttpSession();
    }

    @Test
    public void testCadastrar() throws Exception {
        ModelAndView modelAndView = usuarioController.cadastrar(usuario);

        assertNotNull(modelAndView);
        assertEquals("redirect:/", modelAndView.getViewName());

        List<Usuario> usuarios = usuarioRepository.findAll();
        Usuario resultado = usuarios.stream().filter(u -> u.getEmail().equals(usuario.getEmail())).findFirst().get();

        assertNotNull(resultado);
        assertEquals("userTeste",  resultado.getUser());
    }

    @Test
    public void testLogin() throws Exception {
        usuarioController.cadastrar(usuario);

        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setUser("userTeste");
        usuarioLogin.setSenha("123456");

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioLogin, "usuario");
        ModelAndView modelAndView = usuarioController.login(usuarioLogin, bindingResult, session);

        assertNotNull(modelAndView);
        assertEquals("home/index", modelAndView.getViewName());

        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        assertNotNull(logado);
        assertEquals("userTeste", logado.getUser());
        assertEquals("teste@gmail.com", logado.getEmail());

    }

    @Test
    public void testLogin_ExceptionWrongLogin() throws Exception {
        usuarioController.cadastrar(usuario);

        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setUser("wrong");
        usuarioLogin.setSenha("9283");

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioLogin, "usuario");
        ModelAndView modelAndView = usuarioController.login(usuarioLogin, bindingResult, session);

        assertNotNull(modelAndView);
        assertEquals("login/cadastro", modelAndView.getViewName());
        assertEquals(1, modelAndView.getModel().size());
        assertTrue(modelAndView.getModel().containsKey("usuario"));
        assertNull(session.getAttribute("usuarioLogado"));

    }

    @Test
    public void testLogout() throws Exception {
        usuarioController.cadastrar(usuario);

        Usuario usuarioLogin = new Usuario();
        usuarioLogin.setUser("userTeste");
        usuarioLogin.setSenha("123456");

        BindingResult bindingResult = new BeanPropertyBindingResult(usuarioLogin, "usuario");
        ModelAndView modelAndView = usuarioController.login(usuarioLogin, bindingResult, session);

        assertNotNull(modelAndView);
        assertEquals("home/index", modelAndView.getViewName());

        ModelAndView login = usuarioController.logout(session);
        assertEquals("login/login",  login.getViewName());

        try {
            session.getAttribute("usuarioLogado");
            assertNull(session.getAttribute("usuarioLogado"));
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("invalidated"));
        }
    }

}
