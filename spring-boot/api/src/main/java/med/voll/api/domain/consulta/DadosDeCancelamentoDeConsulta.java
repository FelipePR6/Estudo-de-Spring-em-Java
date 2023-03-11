package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DadosDeCancelamentoDeConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivoCancelamento) {
}
