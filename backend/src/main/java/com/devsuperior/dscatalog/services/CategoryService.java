package com.devsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired  //injeta automaticamente uma dependencia gerenciada pelo spring
	private CategoryRepository repository;
	
	@Transactional(readOnly = true) //em transações que são somente leitura preciso colocar isso
	public List<Category> findAll(){
		return repository.findAll();
	}
	
}
