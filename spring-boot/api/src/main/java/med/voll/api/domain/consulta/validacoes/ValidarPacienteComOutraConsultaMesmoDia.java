package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteComOutraConsultaMesmoDia implements ValidadorAgendamentoDeConsulta  {

    @Autowired
    ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){

        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacienteTemOutraConsultaNoMesmoHorario = consultaRepository.existsByPacienteIdAndDataBetween(dados.idMedico(), primeiroHorario, ultimoHorario);

        if(pacienteTemOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("Paciente já possui outra consulta agendada nesse horario");
        }

    }

}
