// src/main/java/com/ejemplo/crud/controller/PersonaController.java
package com.ejemplo.crud.controller;

import com.ejemplo.crud.model.Persona;
import com.ejemplo.crud.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*") // Para permitir requests del frontend
public class PersonaController {
    
    @Autowired
    private PersonaService personaService;
    
    @GetMapping
    public List<Persona> getAllPersonas() {
        return personaService.getAllPersonas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Optional<Persona> persona = personaService.getPersonaById(id);
        return persona.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Persona createPersona(@RequestBody Persona persona) {
        return personaService.createPersona(persona);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona personaDetails) {
        Persona updatedPersona = personaService.updatePersona(id, personaDetails);
        return updatedPersona != null ? ResponseEntity.ok(updatedPersona) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        boolean deleted = personaService.deletePersona(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/search/nombre")
    public List<Persona> searchByNombre(@RequestParam String nombre) {
        return personaService.searchByNombre(nombre);
    }
    
    @GetMapping("/search/apellido")
    public List<Persona> searchByApellido(@RequestParam String apellido) {
        return personaService.searchByApellido(apellido);
    }
}
