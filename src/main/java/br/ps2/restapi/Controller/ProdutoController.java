package br.ps2.restapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.ps2.restapi.Modelo.Produto;
import br.ps2.restapi.Repositorio.ProdutoRepository;

@Tag(name = "Produto", description = "Produto management APIs")
@RestController
@RequestMapping(value = "/api")
public class ProdutoController {
    @Autowired
    private ProdutoRepository _produtoRepository;

    @Operation(
        summary = "Busca de todos os produtos do Banco",
        description = "Este comando vai buscar todos aqueles produtos que tiverem cadastrados no Banco de Dados",
        tags = { "Produto", "GET" })
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

    @Operation(
        summary = "Busca de produto por ID",
        description = "Este comando vai buscar aquele produto que tem o mesmo id passado no parametro",
        tags = { "Produto", "GET" })
    //GET BY ID
    @GetMapping(value = "/produto/{id}")
    public ResponseEntity<Produto> GetById(@PathVariable(value = "id") long id){
        try {
            if(id <= 0) {
                throw new IllegalArgumentException("O id deve ser maior que zero");
            }
            Optional<Produto> produto = _produtoRepository.findById(id);
            if(produto.isPresent()) {
                return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
        summary = "Adiciona um produto no Banco",
        description = "Este comando vai adicionar um produto no banco de dados, que forem passados via API",
        tags = { "Produto", "POST" })
    //POST 
    @PostMapping(value = "/produto")
    public Produto postProduto(@RequestBody Produto produto){
        try {
            return _produtoRepository.save(produto);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade de dados ao salvar produto", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }


    @Operation(
        summary = "Altera um produto no Banco",
        description = "Este comando vai alterar um produto no banco de dados, escolhido via ID",
        tags = { "Produto", "PUT" })
    //PUT
    @PutMapping(value = "/produto/{id}")
    public ResponseEntity<Produto> Put(@PathVariable(value = "id") long id, @RequestBody Produto newProduto){
        try {
            Optional<Produto> oldProduto = _produtoRepository.findById(id);
            if(oldProduto.isPresent()){
                Produto produto = oldProduto.get();
                produto.setNome(newProduto.getNome());
                produto.setMarca(newProduto.getMarca());
                produto.setPreco(newProduto.getPreco());
                _produtoRepository.save(produto);
                return new ResponseEntity<Produto>(produto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Erro de integridade de dados ao salvar produto", e);
        }
    }

    @Operation(
        summary = "Deleta um produto no Banco",
        description = "Este comando vai delatar um produto no banco de dados, escolhido via ID",
        tags = { "Produto", "DELETE" })
    //DELETE 
    @DeleteMapping(value = "/produto/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id){
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("O id deve ser maior que zero");
            }
            Optional<Produto> produto = _produtoRepository.findById(id);
            if(produto.isPresent()){
                _produtoRepository.delete(produto.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}