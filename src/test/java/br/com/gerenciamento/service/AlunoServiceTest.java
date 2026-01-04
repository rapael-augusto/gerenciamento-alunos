package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private ServiceAluno serviceAluno;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Augusto");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);
    }

    @Test
    public void testGetById() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        Aluno resultado = serviceAluno.getById(1L);

        assertEquals("Augusto", resultado.getNome());
        verify(alunoRepository).findById(1L);
    }

    @Test
    public void testGetById_ExceptionNonExistentId() {
        when(alunoRepository.findById(67L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> serviceAluno.getById(67L));
    }

    @Test
    public void testFindAll() {
        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Raphael");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        serviceAluno.save(aluno2);

        List<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno);
        alunos.add(aluno2);

        when(alunoRepository.findAll()).thenReturn(alunos);

        List<Aluno> resultado = serviceAluno.findAll();

        assertEquals(2, resultado.size());
        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() {
        Long idAluno = 1L;
        doNothing().when(alunoRepository).deleteById(idAluno);

        serviceAluno.deleteById(idAluno);

        verify(alunoRepository, times(1)).deleteById(idAluno);
    }

//    @Test
//    public void getById() {
//        Aluno aluno = new Aluno();
//        aluno.setId(1L);
//        aluno.setNome("Vinicius");
//        aluno.setTurno(Turno.NOTURNO);
//        aluno.setCurso(Curso.ADMINISTRACAO);
//        aluno.setStatus(Status.ATIVO);
//        aluno.setMatricula("123456");
//        this.serviceAluno.save(aluno);
//
//        Aluno alunoRetorno = this.serviceAluno.getById(1L);
//        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
//    }
//
//    @Test
//    public void salvarSemNome() {
//        Aluno aluno = new Aluno();
//        aluno.setId(1L);
//        aluno.setTurno(Turno.NOTURNO);
//        aluno.setCurso(Curso.ADMINISTRACAO);
//        aluno.setStatus(Status.ATIVO);
//        aluno.setMatricula("123456");
//        Assert.assertThrows(ConstraintViolationException.class, () -> {this.serviceAluno.save(aluno);});
//    }
}