package med.voll.api.domain.consulta.cancelamento;

import med.voll.api.domain.consulta.DadosDeCancelamentoDeConsulta;

public interface ValidadorCancelamentoDeConsulta {

    public void validar(DadosDeCancelamentoDeConsulta dados);

}
