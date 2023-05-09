package br.ps2.restapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import br.ps2.restapi.Modelo.Cidade;
import br.ps2.restapi.Repositorio.CidadeRepository;

@RestController
@RequestMapping(value = "/api")
public class CidadeController {
    @Autowired
    private CidadeRepository _CidadeRepository;

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

    //GET BY ID
    @GetMapping(value = "/cidade/{id}")
    public ResponseEntity<Cidade> GetById(@PathVariable(value = "id") long id)
    {
        Optional<Cidade> cidade = _CidadeRepository.findById(id);
        if(cidade.isPresent())
            return new ResponseEntity<Cidade>(cidade.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //POST 
    @PostMapping(value = "/cidade")
    public Cidade postCidade(@RequestBody Cidade cidade){
        return _CidadeRepository.save(cidade);
    }


    
    //PUT
    @PutMapping(value = "/cidade/{id}")
    public ResponseEntity<Cidade> Put(@PathVariable(value = "id") long id, @RequestBody Cidade newCidade)
    {
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
    }


    //DELETE 
    @DeleteMapping(value = "/cidade/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<Cidade> cidade = _CidadeRepository.findById(id);
        if(cidade.isPresent()){
            _CidadeRepository.delete(cidade.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}