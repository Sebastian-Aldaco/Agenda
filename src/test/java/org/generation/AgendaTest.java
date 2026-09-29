package org.generation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgendaTest {
    @Test
    void addsContactsWithinCapacityAndRejectsDuplicates() {
        Agenda agenda = new Agenda(1);
        Contacto contacto = new Contacto("Ana", "López", "Ruiz", "123", "", "", 1990, "");

        assertTrue(agenda.agregarContacto(contacto));
        assertFalse(agenda.agregarContacto(
                new Contacto("ANA", "LÓPEZ", "RUIZ", "456", "", "", 1991, "")
        ));
        assertFalse(agenda.agregarContacto(new Contacto("Luis", "Díaz", "Pérez", "789", "", "", 1988, "")));
        assertEquals(1, agenda.obtenerContactos().length);
        assertEquals(0, agenda.espacioLibres());
    }

    @Test
    void updatesContactWithoutAllowingDuplicates() {
        Agenda agenda = new Agenda();
        Contacto contacto = new Contacto("Ana", "López", "Ruiz", "123", "", "", 1990, "");
        Contacto otro = new Contacto("Luis", "Díaz", "Pérez", "789", "", "", 1988, "");
        assertTrue(agenda.agregarContacto(contacto));
        assertTrue(agenda.agregarContacto(otro));

        Contacto duplicado = new Contacto("Luis", "Díaz", "Pérez", "000", "", "", 2000, "");
        assertFalse(agenda.actualizarContacto(contacto, duplicado));

        Contacto actualizado = new Contacto("Ana María", "López", "Ruiz", "555", "ana@example.com",
                "Monterrey", 1992, "911");
        assertTrue(agenda.actualizarContacto(contacto, actualizado));
        assertEquals("Ana María", contacto.getNombre());
        assertEquals("555", contacto.getTelefono());
        assertEquals("ana@example.com", contacto.getCorreo());
        assertEquals(1992, contacto.getAnioNacimiento());
    }

    @Test
    void returnsDefensiveContactArrayAndRemovesOnlyTheSelectedInstance() {
        Agenda agenda = new Agenda();
        Contacto contacto = new Contacto("Ana", "López", "Ruiz", "123", "", "", 1990, "");
        assertTrue(agenda.agregarContacto(contacto));

        Contacto[] snapshot = agenda.obtenerContactos();
        assertNotSame(snapshot, agenda.obtenerContactos());
        snapshot[0] = null;
        assertEquals(1, agenda.obtenerContactos().length);

        assertFalse(agenda.quitarContacto(new Contacto("Ana", "López", "Ruiz", "123", "", "", 1990, "")));
        assertTrue(agenda.quitarContacto(contacto));
        assertEquals(0, agenda.obtenerContactos().length);
        assertEquals(10, agenda.espacioLibres());
    }
}
