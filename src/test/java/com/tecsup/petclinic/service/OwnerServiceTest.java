package com.tecsup.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tecsup.petclinic.domain.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OwnerServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceTest.class);
	@Autowired
	private OwnerService ownerservice;

	/**
	 * 
	 */
//	@Test
//	public void testFindOwnerById() {
//
//		long ID = 1;
//		String NAME = "Leo";
//		Owner owner = null;
//		
//		try {
//			owner = OwnerService.findById(ID);
//		} catch (OwnerNotFoundException e) {
//			fail(e.getMessage());
//		}
//		logger.info("" + owner);
//
//		assertEquals(NAME, owner.getFirstName());
//
//	}
	
	@Test
	public void testOwnerByName() {

		String FIND_FIRST_NAME = "Peter";
		int SIZE_EXPECTED = 1;

		List<Owner> propietarios = ownerservice.findByFirstName(FIND_FIRST_NAME);

		assertEquals(SIZE_EXPECTED, propietarios.size());
	}
	
	@Test
	public void testOwnerByLastName() {

		String LAST_NAME = "Davis";
		Owner owner;
		List<Owner> propietarios = ownerservice.findByLastName(LAST_NAME);
		owner = propietarios.get(0);
		
		assertEquals(LAST_NAME, owner.getLastName());
		logger.info("Propietario con el apellido '" + LAST_NAME + "' encontrado.");
	}
	
	@Test
	public void testOwnerByCity() {

		String OWNER_CITY = "Coleman";
		int SIZE_EXPECTED = 1;
		
		List<Owner> propietarios = ownerservice.findByCity(OWNER_CITY);
		
		assertEquals(SIZE_EXPECTED, propietarios.size());
		logger.info("Propietario con la ciudad '" + OWNER_CITY + "' encontrado.");
	}

	@Test
	public void testOwnerDelete() throws OwnerNotFoundException {
		
		String OWNER_FIRST_NAME = "Renzo";
		String OWNER_LAST_NAME = "Villaverde";
		String OWNER_CITY = "lima";

		Owner nowOwner = new Owner(OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_CITY);
		nowOwner = ownerservice.create(nowOwner);
		logger.info("Propietario creado: " + nowOwner);

		try {
			ownerservice.delete(nowOwner.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
			
		try {
			ownerservice.findById(nowOwner.getId());
			assertTrue(false);
		} catch (OwnerNotFoundException e) {
			assertTrue(true);
		} 
				

	}
	
	@Test
	public void testVerificOwner() {
		
		String OWNER_FIRST_NAME = "Renzo";
		String OWNER_LAST_NAME = "Villaverde";
		String OWNER_CITY = "lima";
		
		Owner nowOwner = new Owner(OWNER_FIRST_NAME, OWNER_LAST_NAME, OWNER_CITY);
		nowOwner = ownerservice.create(nowOwner);
		
		try {
			Owner ownerFound = ownerservice.findById(nowOwner.getId());
			logger.info("Existe Oner");
		}catch (OwnerNotFoundException e) {
			logger.info("Owner no ha sido creado");
		}
		
		Iterable<Owner> owners = ownerservice.findAll();
		
		while(owners.iterator().hasNext()) {
			try {
				Owner ownerFound = ownerservice.findById(nowOwner.getId());
				logger.info("Owner con el id: "+ ownerFound.getId() + " si existe");
				break;
			}catch (OwnerNotFoundException e) {
				logger.info("Owner no existe");
			}
		}
	}
}







