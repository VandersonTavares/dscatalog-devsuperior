package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired // injeta automaticamente uma dependencia gerenciada pelo spring
	private CategoryRepository repository;

	// Retorna uma lista de categorias do banco
	@Transactional(readOnly = true) // em transações que são somente leitura preciso colocar isso
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

		// ou
		/*
		 * List<CategoryDTO> listDto = new ArrayList<>(); for(Category cat : list) {
		 * listDto.add(new CategoryDTO(cat)); }
		 * 
		 * return listDto;
		 */
	}

	// retorna uma categoria por id
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		// Category entity = obj.get(); // o get do Optional obtem o objeto dentro do
		// optional
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not Found")); // permite chamar uma
																									// chamada de
																									// exceção caso o
																									// obj não seja
																									// encontrado error
																									// 500
		return new CategoryDTO(entity); // o método retorna um DTO e não a entity então precisamos do new
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity); 
		
	}

}
