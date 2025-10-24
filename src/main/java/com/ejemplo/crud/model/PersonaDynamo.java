// src/main/java/com/ejemplo/crud/service/PersonaDynamoService.java
package com.ejemplo.crud.service;

import com.ejemplo.crud.model.PersonaDynamo;
import com.ejemplo.crud.repository.PersonaDynamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaDynamoService {
    
    @Autowired
    private PersonaDynamoRepository personaRepository;
    
    public List<PersonaDynamo> getAllPersonas() {
        List<PersonaDynamo> personas = new ArrayList<>();
        personaRepository.findAll().forEach(personas::add);
        return personas;
    }
    
    public Optional<PersonaDynamo> getPersonaByNombreApellido(String nombre, String apellido) {
        return personaRepository.findByNombreAndApellido(nombre, apellido);
    }
    
    public PersonaDynamo createPersona(PersonaDynamo persona) {
        return personaRepository.save(persona);
    }
    
    public PersonaDynamo updatePersona(String nombre, String apellido, PersonaDynamo personaDetails) {
        Optional<PersonaDynamo> optionalPersona = personaRepository.findByNombreAndApellido(nombre, apellido);
        if (optionalPersona.isPresent()) {
            PersonaDynamo persona = optionalPersona.get();
            persona.setEmail(personaDetails.getEmail());
            persona.setTelefono(personaDetails.getTelefono());
            persona.setEdad(personaDetails.getEdad());
            persona.setDireccion(personaDetails.getDireccion());
            return personaRepository.save(persona);
        }
        return null;
    }
    
    public boolean deletePersona(String nombre, String apellido) {
        Optional<PersonaDynamo> persona = personaRepository.findByNombreAndApellido(nombre, apellido);
        if (persona.isPresent()) {
            personaRepository.delete(persona.get());
            return true;
        }
        return false;
    }
    
    public List<PersonaDynamo> searchByNombre(String nombre) {
        return personaRepository.findByNombre(nombre);
    }
    
    public List<PersonaDynamo> searchByApellido(String apellido) {
        return personaRepository.findByApellido(apellido);
    }
}
