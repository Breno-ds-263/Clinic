package com.Breno.Clinic.model.entities;

public class ItemExame {

    private int codigo;
    private String valorIndicador;
    private String observacao;

    private Exame exame;
    private IndicadorExame indicadorExame;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getValorIndicador() {
        return valorIndicador;
    }

    public void setValorIndicador(String valorIndicador) {
        this.valorIndicador = valorIndicador;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public IndicadorExame getIndicadorExame() {
        return indicadorExame;
    }

    public void setIndicadorExame(IndicadorExame indicadorExame) {
        this.indicadorExame = indicadorExame;
    }
}
