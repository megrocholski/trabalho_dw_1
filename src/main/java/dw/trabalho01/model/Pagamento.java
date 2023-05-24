package dw.trabalho01.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento")
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cod_pagamento;

	@Column
	private short ano;

	@Column
	private short mes;

	@Column
	private double valor;

	@ManyToOne
	// @JoinColumn(name = "")
	private Jogador jogador;

	public Pagamento() {

	}

	public Pagamento(short ano, short mes, double valor, Jogador jogador) {
		this.ano = ano;
		this.mes = mes;
		this.valor = valor;
		this.jogador = jogador;
	}

	public short getAno() {
		return ano;
	}

	public void setAno(short ano){ 
		this.ano = ano;
	}

	public short getMes() {
		return mes;
	}

	public void setMes(short mes){ 
		this.mes = mes;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor){ 
		this.valor = valor;
	}
	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador){ 
		this.jogador = jogador;
	}
}
