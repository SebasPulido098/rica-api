package ricaapi.investigadores.aplicacion;

import ricaapi.investigadores.dominio.CorreoDuplicadoException;
import ricaapi.investigadores.dominio.Investigador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvestigadorFactoryTest {

    @Mock
    private RepositorioInvestigadores repositorioInvestigadores;

    @InjectMocks
    private InvestigadorFactory investigadorFactory;

    @Test
    void crearRechazaCorreoInstitucionalDuplicado() {
        String correo = "ana.torres@uptc.edu.co";
        when(repositorioInvestigadores.existeCorreo(correo)).thenReturn(true);

        assertThrows(CorreoDuplicadoException.class, () -> investigadorFactory.crear(
                "Ana Torres", correo, "GIT-UPTC"));

        verify(repositorioInvestigadores).existeCorreo(correo);
    }

    @Test
    void crearConsultaCorreoYConstruyeInvestigadorCuandoNoEstaDuplicado() {
        String correo = "ana.torres@uptc.edu.co";
        when(repositorioInvestigadores.existeCorreo(correo)).thenReturn(false);

        Investigador investigador = investigadorFactory.crear("Ana Torres", correo, "GIT-UPTC");

        assertEquals("Ana Torres", investigador.getNombreCompleto());
        assertEquals(correo, investigador.getCorreoInstitucional().valor());
        assertEquals("GIT-UPTC", investigador.getGrupoInvestigacion());
        verify(repositorioInvestigadores).existeCorreo(correo);
    }
}
