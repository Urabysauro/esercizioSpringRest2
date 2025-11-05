package com.azienda.esercizioSpringRest2.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azienda.esercizioSpringRest2.model.Localita;
import com.azienda.esercizioSpringRest2.service.Servizio;



@RestController
@RequestMapping(path = "/rest",produces = {"application/json"})
@CrossOrigin(origins = {"*"})
public class LocalitaController {

	@Autowired
	private Servizio servizio;
		
	

	public  LocalitaController () {
		
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Localita> getById(@PathVariable("id")Integer id){
		try {
			Localita l=servizio.getId(id);

			HttpStatus httpStatus = l!=null? HttpStatus.OK : HttpStatus.NOT_FOUND;
			return new ResponseEntity<Localita>(l,httpStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get2/{nome}")
	public ResponseEntity<Localita> getByNome(@PathVariable("nome")String nome){
		try {
			Localita l=servizio.getbyNome(nome);

			HttpStatus httpStatus = l!=null? HttpStatus.OK : HttpStatus.NOT_FOUND;
			return new ResponseEntity<Localita>(l,httpStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get/{tipo}/{temperatura}")
	public ResponseEntity<List<Localita>> getByTemperatura(@PathVariable("tipo")String tipo,@PathVariable("temperatura")Float temperatura){
		try {
			List<Localita> l = servizio.getTemperatura(tipo, temperatura);
			HttpStatus httpStatus = l!=null? HttpStatus.OK : HttpStatus.NOT_FOUND;
			return new ResponseEntity<List<Localita>>(l,httpStatus);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localita>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping(path="/inserisci2",consumes = {"application/json"})
	public ResponseEntity<Localita> inserisci2(@RequestBody Localita loc){
		try {
			Localita l = servizio.getbyNome(loc.getNome());
			HttpStatus httpStatus = null;
			if(l!=null) {
				httpStatus=HttpStatus.BAD_REQUEST;
			}else {
				httpStatus=HttpStatus.CREATED;
				servizio.addLocalita(loc);
			}
			return new ResponseEntity<Localita>(loc,httpStatus);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PutMapping(path="/aggiornaTotale",consumes = {"application/json"})
	public ResponseEntity<Localita> aggiornaTotale(@RequestBody Localita loc){
		try {
			Localita l = servizio.getId(loc.getId());			
			HttpStatus httpStatus = null;
			if(l!=null) {
				if( loc.getNome()!=null && !loc.getNome().isEmpty() && loc.getTemperatura()!=null) {
					httpStatus=HttpStatus.OK;
				Localita l2=servizio.update(l.getId(), l.getNome(), l.getTemperatura(), loc.getNome(), loc.getTemperatura());
					return new ResponseEntity<Localita>(l2,httpStatus);
				}
				httpStatus = HttpStatus.BAD_REQUEST;
				return new ResponseEntity<Localita>(httpStatus);
			}else {
				httpStatus=HttpStatus.NOT_FOUND;
				return new ResponseEntity<Localita>(httpStatus);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PatchMapping(path="/aggiornaParziale",consumes = {"application/json"})
	public ResponseEntity<Localita> aggiornaParziale(@RequestBody Localita loc){
		try {
			Localita l = servizio.getId(loc.getId());

			HttpStatus httpStatus = null;
			if(l!=null) {
				httpStatus=HttpStatus.OK;
				servizio.updateParziale(l.getId(), loc.getNome(), loc.getTemperatura());
//				if(loc.getNome()!=null && !loc.getNome().isEmpty()) {
//					l.setNome(loc.getNome());
//				}
//				if(loc.getTemperatura()!=null) {
//					l.setTemperatura(loc.getTemperatura());
//				}
				return new ResponseEntity<Localita>(httpStatus);

			}else {
				httpStatus=HttpStatus.NOT_FOUND;
				return new ResponseEntity<Localita>(httpStatus);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/cancella2/{id}")
	public ResponseEntity<Void> cancella2(@PathVariable("id")Integer id) {
		try {
			Localita l = servizio.getId(id);
			if(l==null) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
			servizio.deleteLocalita(l.getId());
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	@GetMapping("/elenco")
	public List<Localita> elenco(){
		return servizio.read();
	}

	@DeleteMapping("/cancella/{tipo}/{temperatura}")
	public ResponseEntity<Void> cancellaTempOver(@PathVariable("tipo")String tipo,@PathVariable("temperatura") Float temperatura) {
		try {
			List<Localita> loc=servizio.deleteTemperatura(tipo, temperatura);
			if(loc.isEmpty()) {
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
