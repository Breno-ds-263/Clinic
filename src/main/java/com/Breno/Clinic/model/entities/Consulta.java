package com.Breno.Clinic.model.entities;

import java.time.LocalDateTime;

public class Consulta {

    private int codigo;
    private LocalDateTime dataHoraVolta;
    private String observacao;

    private Paciente paciente;
    private Medico medico;

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDataHoraVolta() {
        return dataHoraVolta;
    }

    public void setDataHoraVolta(LocalDateTime dataHoraVolta) {
        this.dataHoraVolta = dataHoraVolta;
    }

    public String getObservarcao() {
        return observacao;
    }

    public void setObservarcao(String observarcao) {
        this.observacao = observarcao;
    }
}