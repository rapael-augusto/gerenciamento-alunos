package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
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
    void setUp(){
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Augusto");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        serviceAluno.save(aluno);
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