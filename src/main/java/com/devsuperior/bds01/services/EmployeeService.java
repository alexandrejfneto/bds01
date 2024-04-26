package com.devsuperior.bds01.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.entities.Department;
import com.devsuperior.bds01.entities.Employee;
import com.devsuperior.bds01.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;

	@Transactional(readOnly = true)
	public Page<EmployeeDTO> findAllPaged(Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(
				pageable.getPageNumber(),pageable.getPageSize(),Sort.by("name"));
		Page<Employee> page = repository.findAll(pageRequest);
		Page<EmployeeDTO> pageDTO = page.map(x -> new EmployeeDTO(x));
		return pageDTO;
	}
	
	@Transactional
	public EmployeeDTO insert (EmployeeDTO dto) {
			Department department = new Department(dto.getDepartmentId(), null);
			Employee entity = new Employee(null, dto.getName(),dto.getEmail(),department);
			entity = repository.save(entity);
			return new EmployeeDTO(entity);
	}

}
