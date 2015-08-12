/**
 * 
 */
package leilaoDeCentavos.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import leilaoDeCentavos.enumarator.ETipoAuditoria;
import leilaoDeCentavos.factory.AuditoriaFactory;
import leilaoDeCentavos.funcoes.Funcoes;
import leilaoDeCentavos.intercept.AcessoIntercept;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 ); Funcionalidade da Classe:
 *
 */
@SessionScoped
@Named
public class UsuarioLogado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AcessoIntercept acessoIntercept;

	private Usuario usuario;
	private Boolean logado;

	public String urlNegada;

	private AuditoriaFactory af = new AuditoriaFactory();
	private Auditoria auditoria;

	public UsuarioLogado() {
		System.out.println("Inicializando Construtor Usuario Logado");
		usuario = new Usuario();

		logado = false;
	}

	/**
	 * @return the urlNegada
	 */
	public String getUrlNegada() {
		return urlNegada;
	}

	/**
	 * @param urlNegada
	 *            the urlNegada to set
	 */
	public void setUrlNegada(String urlNegada) {
		this.urlNegada = urlNegada;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	
	public Boolean getLogado() {
		return logado;
	}

	public Boolean chkLogado() {

		return this.logado;

	}

	public void setLogin(Usuario usuario, Boolean logado) {

		if (logado) {
			this.usuario = usuario;
			this.logado = true;
			try {
				auditoria = af.getAuditoria(acessoIntercept.getEntityManager());// ETipoAuditoria.Autenticacao

				auditoria.setAtendida(1);
				auditoria.setDataOcorrencia(Calendar.getInstance());
				auditoria.setDescricao("Autenticação de Usuário");
				auditoria.setTipoAuditoria(ETipoAuditoria.Autenticacao);
				// auditoria.setUrlRequisitada("/teste/test");
				auditoria.setUsername(usuario.getUsername());
				auditoria.inserir();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			this.logado = false;
			try {
				auditoria = af.getAuditoria(acessoIntercept.getEntityManager());// ETipoAuditoria.Autenticacao

				auditoria.setAtendida(0);
				auditoria.setDataOcorrencia(Calendar.getInstance());
				auditoria.setDescricao("Autenticação de Usuário Negada");
				auditoria.setTipoAuditoria(ETipoAuditoria.AutenticacaoNegada);
				// auditoria.setUrlRequisitada("");
				auditoria.setUsername(this.usuario.getUsername());
				auditoria.inserir();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	public void setLogout() {

		Funcoes funcoes = new Funcoes();
		funcoes.logout();

		try {
			System.out.println("Gravando auditoria de logout!");
			auditoria = af.getAuditoria(acessoIntercept.getEntityManager());// ETipoAuditoria.AutenticacaoLogout

			auditoria.setAtendida(1);
			auditoria.setDataOcorrencia(Calendar.getInstance());
			auditoria.setDescricao("Autenticação de Logout de Usuário");
			auditoria.setTipoAuditoria(ETipoAuditoria.AutenticacaoLogout);
			// auditoria.setUrlRequisitada("");
			auditoria.setUsername(usuario.getUsername());
			auditoria.inserir();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			this.logado = false;
			this.usuario = null;
		}

	}

	
}
