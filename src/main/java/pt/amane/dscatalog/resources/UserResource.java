package pt.amane.dscatalog.resources;

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

import pt.amane.dscatalog.dtos.UserDTO;
import pt.amane.dscatalog.dtos.UserInsertDTO;
import pt.amane.dscatalog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	
	@Autowired
	private UserService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping
	public ResponseEntity<Page<UserDTO>> finadAll(Pageable pageable){
		
		// PARAMETROS: page, size, sort
		Page<UserDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody UserInsertDTO dto) {
		UserDTO newDtdto = service.create(dto);
		URI url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(url).body(newDtdto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
