package br.com.financias.trabalho.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastosDashboardResponseDTO {

    private double valorTotal;

    private double valorContas;

    private double valorSaude;

    private double valorComida;

    private double valorTransporte;

    private double valorInvestimento;

    private double valorDoacao;

    private double valorLazer;

    private double valorAssinaturas;

    private double valorEducacao;

    private double valorOutros;

    private double valorReceita;

    public double valorMeta;

    public double getValorRestante() {
        return valorReceita - valorTotal;
    }

}
