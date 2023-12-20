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
    void getPersona() {
    }

    @Test
    void updatePersona() {
    }

    @Test
    void deletePersona() {
    }
}