package br.ps2.restapi.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import br.ps2.restapi.Modelo.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    List<Cidade> findByNomeContaining(String nome);
    List<Cidade> findByEstadoContaining(String estado);
}