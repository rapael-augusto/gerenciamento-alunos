package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    private Aluno aluno;

    @BeforeEach
    public void setUp() {
        aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setId(1L);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setMatricula("654321");
    }

    @Test
    public void testInserirAluno() throws Exception {
        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");
        ModelAndView modelAndView = alunoController.inserirAluno(aluno, bindingResult);

        assertNotNull(modelAndView);
        assertFalse(bindingResult.hasErrors());

        if(bindingResult.hasErrors()) {
            assertEquals("Aluno/formAluno", modelAndView.getViewName());
        } else {
            assertEquals("redirect:/alunos-adicionados", modelAndView.getViewName());
        }

        List<Aluno> alunos = serviceAluno.findAll();

        assertFalse(alunos.isEmpty());

        Aluno resultado = alunos.stream().filter(a -> a.getMatricula().equals("654321")).findFirst().orElse(null);

        assertNotNull(resultado);
        assertEquals(aluno.getNome(), resultado.getNome());

    }

    @Test
    public void testEditarAluno() throws Exception {
        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");
        alunoController.inserirAluno(aluno, bindingResult);

        aluno.setNome("Mariano");
        alunoController.editar(aluno.getId());

        ModelAndView modelAndView = alunoController.editar(aluno.getId());

        assertNotNull(modelAndView);
        assertEquals("Aluno/editar", modelAndView.getViewName());
        assertTrue(modelAndView.getModel().containsKey("aluno"));

        aluno.setNome("Mariano");

        ModelAndView modelAndViewSalvar = alunoController.editar(aluno);

        assertNotNull(modelAndViewSalvar);
        assertEquals("redirect:/alunos-adicionados", modelAndViewSalvar.getViewName());

        Aluno resultado = serviceAluno.getById(1L);

        assertNotNull(resultado);
        assertEquals("Mariano", resultado.getNome());

    }

    @Test
    public void testRemoverAluno() throws Exception {
        BindingResult bindingResult = new BeanPropertyBindingResult(aluno, "aluno");
        alunoController.inserirAluno(aluno, bindingResult);

        String removido = alunoController.removerAluno(aluno.getId());
        assertNotNull(removido);
        assertEquals("redirect:/alunos-adicionados", removido);

        List<Aluno> alunos = serviceAluno.findAll();
        boolean alunoExiste = alunos.stream().anyMatch(a -> a.getId().equals(1L));
        assertFalse(alunoExiste);
    }

    @Test
    public void testPesquisarAluno_ExceptionNonExistent() {
        ModelAndView modelAndView = alunoController.pesquisarAluno("Inexistente");

        assertNotNull(modelAndView);
        assertEquals("Aluno/pesquisa-resultado", modelAndView.getViewName());

        Map<String, Object> model = modelAndView.getModel();
        assertTrue(model.containsKey("ListaDeAlunos"));

        @SuppressWarnings("unchecked")
        List<Aluno> alunos = (List<Aluno>) model.get("ListaDeAlunos");

        assertNotNull(alunos);
        assertTrue(alunos.isEmpty());
    }

}
