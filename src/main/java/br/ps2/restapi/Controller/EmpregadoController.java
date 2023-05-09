package br.ps2.restapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import br.ps2.restapi.Modelo.Empregado;
import br.ps2.restapi.Repositorio.EmpregadoRepository;

@RestController
@RequestMapping(value = "/api")
public class EmpregadoController {
    @Autowired
    private EmpregadoRepository _EmpregadoRepository;

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

    //GET BY ID
    @GetMapping(value = "/empregado/{id}")
    public ResponseEntity<Empregado> GetById(@PathVariable(value = "id") long id)
    {
        Optional<Empregado> Empregado = _EmpregadoRepository.findById(id);
        if(Empregado.isPresent())
            return new ResponseEntity<Empregado>(Empregado.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //POST 
    @PostMapping(value = "/empregado")
    public Empregado postEmpregado(@RequestBody Empregado Empregado){
        return _EmpregadoRepository.save(Empregado);
    }


    
    //PUT
    @PutMapping(value = "/empregado/{id}")
    public ResponseEntity<Empregado> Put(@PathVariable(value = "id") long id, @RequestBody Empregado newEmpregado)
    {
        Optional<Empregado> oldEmpregado = _EmpregadoRepository.findById(id);
        if(oldEmpregado.isPresent()){
            Empregado Empregado = oldEmpregado.get();
            Empregado.setNome(newEmpregado.getNome());
            _EmpregadoRepository.save(Empregado);
            return new ResponseEntity<Empregado>(Empregado, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //DELETE 
    @DeleteMapping(value = "/empregado/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<Empregado> Empregado = _EmpregadoRepository.findById(id);
        if(Empregado.isPresent()){
            _EmpregadoRepository.delete(Empregado.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}