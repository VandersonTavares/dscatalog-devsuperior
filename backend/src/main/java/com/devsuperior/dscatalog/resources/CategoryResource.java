package com.devsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

//Vai listar as categorias

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	/* PARTE 1 ANTES DA PAGINAÇÃO - esse metodo retorna a lista de todas as categorias
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list); // retorna a lista no corpo da resposta http

		PARTE 0 MOCK
		// List<Category> list = ArrayList<>();
		/*
		 * list.add(new Category(1L, "Book")); 
		 * list.add(new Category(2L, "Eletronics"));
		 * list.add(new Category(3L, "Food"));
		
	}
	*/
	//PARTE 2 CODIGO ACIMA ALTERADO PARA PAGINAÇÃO - Altera depois de fazer todo o crud
	// esse metodo retorna a lista de todas as categorias
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {
		Page<CategoryDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list); 		
	}

	// esse metodo encontra uma categoria por id
	@GetMapping(value = "/{id}") // PathVariable indica que o id do parametro é o mesmo do Value
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);	
		return ResponseEntity.ok().body(dto); //retorna 200
	}
	
	//metodo para inserir uma nova categoria no banco 
	//- quando vamos inserir algo no bd usamos o PostMapping em vez do GetMapping
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto); //retorna o 201
	}
	
	//método para atualizar uma categoria
	//usamos o PutMapping
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto); //retorna 200
	}
	
	//método para deletar uma categoria
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); //retorna 204 - o corpo da resposta está vazio
	}
	
	
}
