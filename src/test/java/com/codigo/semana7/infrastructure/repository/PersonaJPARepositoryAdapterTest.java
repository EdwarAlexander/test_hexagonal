package com.codigo.semana7.infrastructure.repository;

import com.codigo.semana7.domain.model.Persona;
import com.codigo.semana7.infrastructure.entity.PersonaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonaJPARepositoryAdapterTest {

    @Mock
    PersonaJPARepository personaJPARepository;
    @InjectMocks
    PersonaJPARepositoryAdapter personaJPARepositoryAdapter;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        personaJPARepositoryAdapter = new PersonaJPARepositoryAdapter(personaJPARepository);
    }

    @Test
    void createPersonaExitoso() {
        //PREPARAR
        Persona persona = new Persona(1L,"Paul","Rodriguez",new Date(),"Masculino");

        PersonaEntity personaEntity = new PersonaEntity(1L,"Paul","Rodriguez",new Date(),"Masculino");

        //SIMULAR & EJECUTAR
        Mockito.when(personaJPARepository.save(Mockito.any(PersonaEntity.class))).thenReturn(personaEntity);

        Persona personaAdapter = personaJPARepositoryAdapter.createPersona(persona);

        //AFIRMAR
        assertNotNull(personaAdapter);
        assertEquals(persona.getId(),personaAdapter.getId());
    }

    @Test
    void getPersona_IsEmpty() {
        Mockito.when(personaJPARepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());
        Optional<Persona> persona = personaJPARepositoryAdapter.getPersona(1L);
        assertTrue(persona.isEmpty());
    }
    @Test
    void getPersona_ExistingId_ReturnsPersona() {
        PersonaEntity personaEntity = new PersonaEntity(1L,"Paul","Rodriguez",new Date(),"Masculino");
        Mockito.when(personaJPARepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(personaEntity));
        Optional<Persona> persona = personaJPARepositoryAdapter.getPersona(1L);
        assertEquals(persona.get().getId(),personaEntity.getId());
    }

    @Test
    void updatePersona_ExistingIdAndValidPersona_ReturnsUpdatedPersona() {
        Persona persona = new Persona(1L,"Paul","Rodriguez",new Date(),"Masculino");
        PersonaEntity personaEntity = new PersonaEntity(1L,"Paul","Rodriguez",new Date(),"Masculino");
        Mockito.when(personaJPARepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.when(personaJPARepository.save(Mockito.any(PersonaEntity.class))).thenReturn(personaEntity);
        Optional<Persona> personaAdapter = personaJPARepositoryAdapter.updatePersona(1l,persona);
        assertEquals(personaAdapter.get().getId(),personaEntity.getId());
    }

    @Test
    void updatePersona_NonExistingId_ReturnsEmptyOptional() {
        Persona persona = new Persona(1L,"Paul","Rodriguez",new Date(),"Masculino");
        Mockito.when(personaJPARepository.existsById(Mockito.any(Long.class))).thenReturn(false);
        Optional<Persona> personaAdapter = personaJPARepositoryAdapter.updatePersona(2l,persona);
        assertTrue(personaAdapter.isEmpty());
    }

    @Test
    void deletePersona_NonExistingId_ReturnsFalse() {
        Mockito.when(personaJPARepository.existsById(Mockito.any(Long.class))).thenReturn(false);
        boolean rpta = personaJPARepositoryAdapter.deletePersona(1l);
        assertFalse(rpta);
        assertEquals(false,rpta);
    }

    @Test
    void deletePersona_ExistingId_ReturnsTrue() {
        Mockito.when(personaJPARepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        boolean rpta = personaJPARepositoryAdapter.deletePersona(1l);
        assertTrue(rpta);
        assertEquals(true,rpta);
    }
}