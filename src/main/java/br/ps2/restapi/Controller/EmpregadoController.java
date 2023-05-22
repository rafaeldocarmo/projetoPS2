package br.ps2.restapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.ps2.restapi.Modelo.Empregado;
import br.ps2.restapi.Repositorio.EmpregadoRepository;

@Tag(name = "Empregado", description = "Empregado management APIs")
@RestController
@RequestMapping(value = "/api")
public class EmpregadoController {
    @Autowired
    private EmpregadoRepository _EmpregadoRepository;


    @Operation(
        summary = "Busca de todos os empregados do Banco",
        description = "Este comando vai buscar todos aqueles empregados que tiverem cadastrados no Banco de Dados",
        tags = { "Empregado", "GET" })
    //GET ALL
    @GetMapping(value = "/empregado")
    public List<Empregado> buscarProdutosPorNome(@RequestParam(required = false) String nome, @RequestParam(required = false) String cargo) {
        if (cargo != null) {
            return _EmpregadoRepository.findByCargoContaining(cargo);
        } else if (nome != null) {
            return _EmpregadoRepository.findByNomeContaining(nome);
        } else {
            return _EmpregadoRepository.findAll();
        }
    }

    @Operation(
        summary = "Busca de empregado por ID",
        description = "Este comando vai buscar aquele empregado que tem o mesmo id passado no parametro",
        tags = { "Empregado", "GET" })
    //GET BY ID
    @GetMapping(value = "/empregado/{id}")
    public ResponseEntity<Empregado> GetById(@PathVariable(value = "id") long id){
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("O id deve ser maior que zero");
            }
            Optional<Empregado> Empregado = _EmpregadoRepository.findById(id);
            if(Empregado.isPresent())
                return new ResponseEntity<Empregado>(Empregado.get(), HttpStatus.OK);
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }        
    }

    @Operation(
        summary = "Adiciona um empregado no Banco",
        description = "Este comando vai adicionar um empregado no banco de dados, que forem passados via API",
        tags = { "Empregado", "POST" })
    //POST 
    @PostMapping(value = "/empregado")
    public Empregado postEmpregado(@RequestBody Empregado Empregado){
        try {
            return _EmpregadoRepository.save(Empregado);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade de dados ao salvar empregado", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar empregado", e);
        }
    }


    @Operation(
        summary = "Altera um empregado no Banco",
        description = "Este comando vai alterar um usuário no banco de dados, escolhido via ID",
        tags = { "Empregado", "PUT" })
    //PUT
    @PutMapping(value = "/empregado/{id}")
    public ResponseEntity<Empregado> Put(@PathVariable(value = "id") long id, @RequestBody Empregado newEmpregado){
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("O id deve ser maior que zero");
            }
            Optional<Empregado> oldEmpregado = _EmpregadoRepository.findById(id);
            if(oldEmpregado.isPresent()){
                Empregado Empregado = oldEmpregado.get();
                Empregado.setNome(newEmpregado.getNome());
                Empregado.setCargo(newEmpregado.getCargo());
                Empregado.setCidade(newEmpregado.getCidade());
                Empregado.setSalario(newEmpregado.getSalario());
                _EmpregadoRepository.save(Empregado);
                return new ResponseEntity<Empregado>(Empregado, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Deleta um empregado no Banco",
        description = "Este comando vai delatar um empregado no banco de dados, escolhido via ID",
        tags = { "Empregado", "DELETE" })
    //DELETE 
    @DeleteMapping(value = "/empregado/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("O id deve ser maior que zero");
            }
            Optional<Empregado> Empregado = _EmpregadoRepository.findById(id);
            if(Empregado.isPresent()){
                _EmpregadoRepository.delete(Empregado.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }    
    }

}