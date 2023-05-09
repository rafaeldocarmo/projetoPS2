package br.ps2.restapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import br.ps2.restapi.Modelo.Produto;
import br.ps2.restapi.Repositorio.ProdutoRepository;

@RestController
@RequestMapping(value = "/api")
public class ProdutoController {
    @Autowired
    private ProdutoRepository _produtoRepository;

    //GET ALL AND BY NAME
    @GetMapping(value = "/produto")
    public List<Produto> buscarProdutosPorNome(@RequestParam(required = false) String nome, @RequestParam(required = false) String marca) {
        if (marca != null) {
            return _produtoRepository.findByMarcaContaining(marca);
        } else if (nome != null) {
            return _produtoRepository.findByNomeContaining(nome);
        } else {
            return _produtoRepository.findAll();
        }
    }

    //GET BY ID
    @GetMapping(value = "/produto/{id}")
    public ResponseEntity<Produto> GetById(@PathVariable(value = "id") long id){
        Optional<Produto> produto = _produtoRepository.findById(id);
        if(produto.isPresent())
            return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //POST 
    @PostMapping(value = "/produto")
    public Produto postProduto(@RequestBody Produto produto){
        return _produtoRepository.save(produto);
    }


    
    //PUT
    @PutMapping(value = "/produto/{id}")
    public ResponseEntity<Produto> Put(@PathVariable(value = "id") long id, @RequestBody Produto newProduto){
        Optional<Produto> oldProduto = _produtoRepository.findById(id);
        if(oldProduto.isPresent()){
            Produto produto = oldProduto.get();
            produto.setNome(newProduto.getNome());
            _produtoRepository.save(produto);
            return new ResponseEntity<Produto>(produto, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    //DELETE 
    @DeleteMapping(value = "/produto/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        Optional<Produto> produto = _produtoRepository.findById(id);
        if(produto.isPresent()){
            _produtoRepository.delete(produto.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}