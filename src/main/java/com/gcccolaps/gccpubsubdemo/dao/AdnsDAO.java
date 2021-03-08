package com.gcccolaps.gccpubsubdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gcccolaps.gccpubsubdemo.entity.Adn;

public interface AdnsDAO extends JpaRepository<Adn, Long>{
	
	@Query(
		       value = "SELECT COUNT(*) FROM adns WHERE tipo = 'M' ",
		       nativeQuery = true
		)
	long cantidadDeMutantes();
	
	@Query(
		       value = "SELECT COUNT(*) FROM adns WHERE tipo = 'H' ",
		       nativeQuery = true
		)
	long cantidadDeHumanos();

}
