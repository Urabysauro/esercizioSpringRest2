package com.azienda.esercizioSpringRest2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azienda.esercizioSpringRest2.exception.ListaVuotaEccezione;
import com.azienda.esercizioSpringRest2.exception.LocalitaEsisteException;
import com.azienda.esercizioSpringRest2.exception.LocalitaNonEsisteEccezione;
import com.azienda.esercizioSpringRest2.model.Localita;
import com.azienda.esercizioSpringRest2.repository.LocalitaRepository;


@Service
@Transactional
public class Servizio {
	
	@Autowired
	private LocalitaRepository localitaRepository;
	
	public List<Localita> read(){
		return localitaRepository.findAll();
	}
	
	public Localita getId(Integer id) {
		try {
		Localita l = localitaRepository.findById(id).orElse(null);
		return l;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Localita getbyNome(String nome) {
		return localitaRepository.findByNome(nome);
	}
	
	public List<Localita> getTemperatura(String tipo,Float temperatura){
		List<Localita> l = read();
		List<Localita> finale = new ArrayList<Localita>();
		for(Localita loc:l) {
			if(tipo.equalsIgnoreCase("Minore")) {
				if(loc.getTemperatura()<temperatura) {
					finale.add(loc);
				}
			}else {
				if(loc.getTemperatura()>temperatura) {
					finale.add(loc);
				}
			}
		}
		return finale;
	}
	
	public Localita addLocalita(Localita loc) throws LocalitaEsisteException  {
		Localita l = localitaRepository.findByNomeAndTemperatura(loc.getNome(),loc.getTemperatura());
		if(l==null) {
		localitaRepository.save(loc);
		return loc;
		}else {
			throw new LocalitaEsisteException("La localita esiste gia", null);
		}
		
	}
	
	public void deleteLocalita(Integer id) throws LocalitaNonEsisteEccezione {
		Localita l = localitaRepository.findById(id).orElse(null);
		if(l==null) {
			throw new LocalitaNonEsisteEccezione("La localita non esiste", null);
		}
		localitaRepository.deleteById(l.getId());
		
	}
	
	public List<Localita> deleteTemperatura(String tipo,Float temperatura) throws ListaVuotaEccezione {
		List<Localita> l = getTemperatura(tipo, temperatura);
		List<Localita> l2=l;
		if(l.size()==0) {
			throw new ListaVuotaEccezione("Nessun risultato", null);
		}
		localitaRepository.deleteAll(l);
		return l2;
	}
	
	public Localita update(Integer id,String nome,Float temperatura,String newNome,Float newTemperatura ) throws LocalitaNonEsisteEccezione {
		Localita l= localitaRepository.findById(id).orElse(null);
		
		if(l!=null) {
	    l.setNome(newNome);
		l.setTemperatura(newTemperatura);
		localitaRepository.save(l);
		return l;
		}else {
			throw new LocalitaNonEsisteEccezione("La localita non è presente nel db", null);
		}
		
	}
	
	public Localita updateParziale(Integer id,String nome,Float temperatura) throws LocalitaNonEsisteEccezione {
		Localita l= localitaRepository.findById(id).orElse(null);
		if(l==null) {
			throw new LocalitaNonEsisteEccezione("La localita non è presente nel db", null);
		}
		
		if(nome!=null && !nome.isEmpty()) {
			l.setNome(nome);
		}
		if(temperatura!=null) {
			l.setTemperatura(temperatura);
		}
		return l;
	}
	

}
