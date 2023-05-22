package br.ps2.restapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.ps2.restapi.Modelo.Cidade;
import br.ps2.restapi.Repositorio.CidadeRepository;

@Tag(name = "Cidade", description = "Cidade management APIs")
@RestController
@RequestMapping(value = "/api")
public class CidadeController {
    @Autowired
    private CidadeRepository _CidadeRepository;

    @Operation(
        summary = "Busca de todos as cidades do Banco",
        description = "Este comando vai buscar todas aquelas cidades que tiverem cadastrados no Banco de Dados",
        tags = { "Cidade", "GET" })
    //GET ALL
    @GetMapping(value = "/cidade")
    public List<Cidade> buscarProdutosPorNome(@RequestParam(required = false) String nome, @RequestParam(required = false) String estado) {
        if (estado != null) {
            return _CidadeRepository.findByEstadoContaining(estado);
        } else if (nome != null) {
            return _CidadeRepository.findByNomeContaining(nome);
        } else {
            return _CidadeRepository.findAll();
        }
    }

    @Operation(
        summary = "Busca de cidade por ID",
        description = "Este comando vai buscar aquela cidade que tem o mesmo id passado no parametro",
        tags = { "Cidade", "GET" })
    //GET BY ID
    @GetMapping(value = "/cidade/{id}")
    public ResponseEntity<Cidade> GetById(@PathVariable(value = "id") long id){
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("O id deve ser maior que zero");
            }
            Optional<Cidade> cidade = _CidadeRepository.findById(id);
            if(cidade.isPresent()) {
                return new ResponseEntity<Cidade>(cidade.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Adiciona uma cidade no Banco",
        description = "Este comando vai adicionar uma cidade no banco de dados, que forem passados via API",
        tags = { "Cidade", "POST" })
    //POST 
    @PostMapping(value = "/cidade")
    public Cidade postCidade(@RequestBody Cidade cidade){
        try {
            return _CidadeRepository.save(cidade);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade de dados ao salvar cidade", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cidade", e);
        }
    }


    @Operation(
        summary = "Altera uma cidade no Banco",
        description = "Este comando vai alterar uma cidade no banco de dados, escolhido via ID",
        tags = { "Cidade", "PUT" })
    //PUT
    @PutMapping(value = "/cidade/{id}")
    public ResponseEntity<Cidade> Put(@PathVariable(value = "id") long id, @RequestBody Cidade newCidade){
        try {
            Optional<Cidade> oldCidade = _CidadeRepository.findById(id);
            if(oldCidade.isPresent()){
                Cidade cidade = oldCidade.get();
                cidade.setNome(newCidade.getNome());
                cidade.setEstado(newCidade.getEstado());
                cidade.setPopulacao(newCidade.getPopulacao());
                _CidadeRepository.save(cidade);
                return new ResponseEntity<Cidade>(cidade, HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade de dados ao salvar cidade", e);
        }
    }

    

    @Operation(
        summary = "Deleta uma cidade no Banco",
        description = "Este comando vai delatar uma cidade no banco de dados, escolhido via ID",
        tags = { "Cidade", "DELETE" })
    //DELETE 
    @DeleteMapping(value = "/cidade/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("O id deve ser maior que zero");
            }
            Optional<Cidade> cidade = _CidadeRepository.findById(id);
            if(cidade.isPresent()){
                _CidadeRepository.delete(cidade.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}