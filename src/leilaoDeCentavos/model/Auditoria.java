/**
 * 
 */
package leilaoDeCentavos.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 ); Funcionalidade da Classe:
 *
 */
@Entity
public class Auditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "text")
	private String sqlGerado;
	@Column(columnDefinition = "text")
	private String erro;
	@Column(columnDefinition = "text")
	private String erroDetalhe;
	@Column(columnDefinition = "text")
	private String urlSolitada;
	@Column(columnDefinition = "text")
	private String urlAtendida;

	private Integer atendida;

	@Column(columnDefinition = "text")
	@Transient
	private UsuarioLogado usuarioLogado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Calendar dataOcorrencia;

	public Auditoria() {
		// super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSqlGerado() {
		return sqlGerado;
	}

	public void setSqlGerado(String sqlGerado) {
		this.sqlGerado = sqlGerado;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getErroDetalhe() {
		return erroDetalhe;
	}

	public void setErroDetalhe(String erroDetalhe) {
		this.erroDetalhe = erroDetalhe;
	}

	public String getUrlSolitada() {
		return urlSolitada;
	}

	public void setUrlSolitada(String urlSolitada) {
		this.urlSolitada = urlSolitada;
	}

	public String getUrlAtendida() {
		return urlAtendida;
	}

	public void setUrlAtendida(String urlAtendida) {
		this.urlAtendida = urlAtendida;
	}

	public Integer getAtendida() {
		return atendida;
	}

	public void setAtendida(Integer atendida) {
		this.atendida = atendida;
	}

	public UsuarioLogado getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioLogado usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Calendar getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(Calendar dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

}
