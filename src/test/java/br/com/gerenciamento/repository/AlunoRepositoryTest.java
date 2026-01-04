package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @BeforeEach
    public void setUp() {
        alunoRepository.deleteAll();
    }

    @Test
    public void testFindByStatusAtivo(){
        Aluno ativo = new Aluno();
        ativo.setId(2L);
        ativo.setNome("Raphael");
        ativo.setTurno(Turno.NOTURNO);
        ativo.setCurso(Curso.INFORMATICA);
        ativo.setStatus(Status.ATIVO);
        ativo.setMatricula("654321");
        alunoRepository.save(ativo);

        Aluno inativo = new Aluno();
        inativo.setId(1L);
        inativo.setNome("Augusto");
        inativo.setTurno(Turno.NOTURNO);
        inativo.setCurso(Curso.INFORMATICA);
        inativo.setStatus(Status.INATIVO);
        inativo.setMatricula("654321");
        alunoRepository.save(inativo);

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();

        assertEquals(1, ativos.size());
        assertTrue(ativos.stream().allMatch(a -> Status.ATIVO.equals(a.getStatus())));
    }

    @Test
    public void testFindByStatusInativo(){
        Aluno ativo = new Aluno();
        ativo.setId(2L);
        ativo.setNome("Raphael");
        ativo.setTurno(Turno.NOTURNO);
        ativo.setCurso(Curso.INFORMATICA);
        ativo.setStatus(Status.ATIVO);
        ativo.setMatricula("654321");
        alunoRepository.save(ativo);

        Aluno inativo = new Aluno();
        inativo.setId(1L);
        inativo.setNome("Augusto");
        inativo.setTurno(Turno.NOTURNO);
        inativo.setCurso(Curso.INFORMATICA);
        inativo.setStatus(Status.INATIVO);
        inativo.setMatricula("654321");
        alunoRepository.save(inativo);

        List<Aluno> inativos = alunoRepository.findByStatusInativo();

        assertEquals(1, inativos.size());
        assertTrue(inativos.stream().allMatch(a -> Status.INATIVO.equals(a.getStatus())));
    }

    @Test
    public void testFindAll_EmptyRepository(){
        List<Aluno> vazia = alunoRepository.findAll();

        assertNotNull(vazia);
        assertTrue(vazia.isEmpty());
    }

    @Test
    public void testFindByNomeContainingIgnoringCase(){
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Fulano Um");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("654321");
        alunoRepository.save(aluno1);

        List<Aluno> busca1 = alunoRepository.findByNomeContainingIgnoreCase("um");
        assertEquals(1, busca1.size());
        assertEquals("Fulano Um", busca1.get(0).getNome());

        List<Aluno> busca2 = alunoRepository.findByNomeContainingIgnoreCase("UM");
        assertEquals(1, busca2.size());
        assertEquals("Fulano Um", busca2.get(0).getNome());
    }

}