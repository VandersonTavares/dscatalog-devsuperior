package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired // injeta automaticamente uma dependencia gerenciada pelo spring
	private CategoryRepository repository;

	/* PARTE 1 NO RESOURCE
	// Retorna uma lista de categorias do banco
	@Transactional(readOnly = true) // em transações que são somente leitura preciso colocar isso
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	*/
	//retorna uma busca paginada 
	@Transactional(readOnly = true) 
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
	}

	// retorna uma categoria por id
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		// Category entity = obj.get(); // o get do Optional obtem o objeto dentro do
		// optional
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found")); // permite chamar
																									// uma
																									// chamada de
																									// exceção caso o
																									// obj não seja
																									// encontrado error
																									// 500
		return new CategoryDTO(entity); // o método retorna um DTO e não a entity então precisamos do new
	}

	//Insere uma nova categoria
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);

	}

	//Atualiza uma categoria por ID
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id); // instancia um obj provisorio, somente qnd mandamos salvar que ele
														// irá ao bd
			entity.setName(dto.getName()); // na categoria so mudamos o nome o id é autoincrrementado pelo bd
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	//deleta uma categoria por ID
	// não coloca o transactional pq precisamos capturar uma exceção no bd
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("id not found "+ id);
		}//para integridade referencial
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
				
	}

}
