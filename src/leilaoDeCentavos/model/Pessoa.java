/**
 * 
 */
package leilaoDeCentavos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 ); Funcionalidade da Classe:
 *
 */
@Entity
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String idPessoa;
	@Column(columnDefinition = "text")
	private String primeiroNome;
	@Column(columnDefinition = "text")
	private String sobrenome;
	@Column(columnDefinition = "text")
	private String nomeCompleto;
	@Column(columnDefinition = "text")
	private Usuario usuario;

}
