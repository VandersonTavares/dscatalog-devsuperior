package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

//Vai listar as categorias

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(){
		
		List<CategoryDTO> list = service.findAll();
		
		//List<Category> list = ArrayList<>();
		/*
		list.add(new Category(1L, "Book"));
		list.add(new Category(2L, "Eletronics"));
		list.add(new Category(3L, "Food"));
		*/
		
		return ResponseEntity.ok().body(list); //retorna a lista no corpo da resposta http
		
	}

}
