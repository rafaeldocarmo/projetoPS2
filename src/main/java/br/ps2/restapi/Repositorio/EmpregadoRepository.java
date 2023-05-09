package br.ps2.restapi.Repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import br.ps2.restapi.Modelo.Empregado;

@Repository
public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {
    List<Empregado> findByNomeContaining(String nome);
    List<Empregado> findByCargoContaining(String cargo);
}