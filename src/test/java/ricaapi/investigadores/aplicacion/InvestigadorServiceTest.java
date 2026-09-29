package ricaapi.investigadores.aplicacion;

import ricaapi.investigadores.dominio.CorreoDuplicadoException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvestigadorServiceTest {

    @Mock
    private RepositorioInvestigadores repositorioInvestigadores;

    @Mock
    private InvestigadorFactory investigadorFactory;

    @InjectMocks
    private InvestigadorService investigadorService;

    @Test
    void registrarRechazaCorreoInstitucionalDuplicado() {
        String nombreCompleto = "Ana Torres";
        String correoInstitucional = "ana.torres@uptc.edu.co";
        String grupoInvestigacion = "GIT-UPTC";

        when(investigadorFactory.crear(nombreCompleto, correoInstitucional, grupoInvestigacion))
                .thenThrow(new CorreoDuplicadoException("Correo institucional duplicado"));

        assertThrows(CorreoDuplicadoException.class, () -> investigadorService.registrar(
                nombreCompleto, correoInstitucional, grupoInvestigacion));

        verify(investigadorFactory).crear(nombreCompleto, correoInstitucional, grupoInvestigacion);
        verifyNoInteractions(repositorioInvestigadores);
    }
}
