// src/main/java/com/ejemplo/crud/service/PersonaService.java
package com.ejemplo.crud.service;

import com.ejemplo.crud.model.Persona;
import com.ejemplo.crud.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    
    @Autowired
    private PersonaRepository personaRepository;
    
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }
    
    public Optional<Persona> getPersonaById(Long id) {
        return personaRepository.findById(id);
    }
    
    public Persona createPersona(Persona persona) {
        return personaRepository.save(persona);
    }
    
    public Persona updatePersona(Long id, Persona personaDetails) {
        Optional<Persona> optionalPersona = personaRepository.findById(id);
        if (optionalPersona.isPresent()) {
            Persona persona = optionalPersona.get();
            persona.setNombre(personaDetails.getNombre());
            persona.setApellido(personaDetails.getApellido());
            persona.setEmail(personaDetails.getEmail());
            persona.setTelefono(personaDetails.getTelefono());
            persona.setEdad(personaDetails.getEdad());
            persona.setDireccion(personaDetails.getDireccion());
            return personaRepository.save(persona);
        }
        return null;
    }
    
    public boolean deletePersona(Long id) {
        if (personaRepository.existsById(id)) {
            personaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public List<Persona> searchByNombre(String nombre) {
        return personaRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public List<Persona> searchByApellido(String apellido) {
        return personaRepository.findByApellidoContainingIgnoreCase(apellido);
    }
}
