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
        Optional<Cidade> Cidade = _CidadeRepository.findById(id);
        if(Cidade.isPresent())
            return new ResponseEntity<Cidade>(Cidade.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //POST 
    @PostMapping(value = "/cidade")
    public Cidade postCidade(@RequestBody Cidade Cidade){
        return _CidadeRepository.save(Cidade);
    }


    
    //PUT
    @PutMapping(value = "/cidade/{id}")
    public ResponseEntity<Cidade> Put(@PathVariable(value = "id") long id, @RequestBody Cidade newCidade)
    {
        Optional<Cidade> oldCidade = _CidadeRepository.findById(id);
        if(oldCidade.isPresent()){
            Cidade Cidade = oldCidade.get();
            Cidade.setNome(newCidade.getNome());
            _CidadeRepository.save(Cidade);
            return new ResponseEntity<Cidade>(Cidade, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //DELETE 
    @DeleteMapping(value = "/cidade/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<Cidade> Cidade = _CidadeRepository.findById(id);
        if(Cidade.isPresent()){
            _CidadeRepository.delete(Cidade.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}