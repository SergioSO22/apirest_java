// src/main/java/com/ejemplo/crud/repository/PersonaRepository.java
package com.ejemplo.crud.repository;

import com.ejemplo.crud.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombreContainingIgnoreCase(String nombre);
    List<Persona> findByApellidoContainingIgnoreCase(String apellido);
}
