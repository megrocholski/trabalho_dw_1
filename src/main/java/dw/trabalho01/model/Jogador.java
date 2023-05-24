package dw.trabalho01.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "jogador")
public class Jogador {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long cod_jogador;

	@Column
	private String nome;

	@Column
	private String email;

	@Column
	private Date dataNasc;

	@OneToMany(cascade = CascadeType.ALL)
	List<Pagamento> pagamentos;

	public Jogador(){}

	public Jogador(String nome, String email, Date dataNasc){
		this.nome = nome;
		this.email = email;
		this.dataNasc = dataNasc;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome){
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}
	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc){
		this.dataNasc = dataNasc;
	}
	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos){
		this.pagamentos = pagamentos;
	}
}
