package com.azienda.esercizioSpringRest2.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.azienda.esercizioSpringRest2.model.Localita;

public interface LocalitaRepository extends JpaRepository<Localita, Integer>{
	public Localita findByNome(String nome);
	public Localita findByNomeAndTemperatura(String nome,Float temperatura);
	
}
