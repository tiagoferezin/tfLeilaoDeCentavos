/**
 * 
 */
package leilaoDeCentavos.intercept;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Enumeration;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

import leilaoDeCentavos.annotation.Publico;
import leilaoDeCentavos.controller.LoginController;
import leilaoDeCentavos.enumarator.ETipoAuditoria;
import leilaoDeCentavos.factory.AuditoriaFactory;
import leilaoDeCentavos.model.Auditoria;
import leilaoDeCentavos.model.UsuarioLogado;
import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AfterCall;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 ); Funcionalidade da Classe:
 *
 */
@Intercepts
public class AcessoIntercept implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	private EntityManagerFactory entityManagerFactory;

	@Inject
	private ControllerMethod controllerMethod;

	private Boolean urlAcessada;

	@Inject
	private HttpServletRequest request;

	@Inject
	private UsuarioLogado usuarioLogado;

	@Inject
	private Result result;

	// @Accepts
	// public boolean accepts() {
	// boolean retorno = true;
	//
	// // / TODAS AS URLS SÃO ACEITAS E VERIFICADAS.
	//
	// // Aceita no autorizador todas as requisições que não tem a anotação
	// // "Publico"
	// // System.out.println("Verificando aceitação: "
	// // + controllerMethod.getClass().getName());
	// // boolean retorno =
	// // !controllerMethod.containsAnnotation(Publico.class);
	// // System.out.println(retorno);
	// //
	// // this.urlAcessada = (!retorno);
	//
	// return retorno;
	// }

	@Accepts
	public boolean accepts() {
		boolean retorno = true;

		// / TODAS AS URLS SÃO ACEITAS E VERIFICADAS.

		// Aceita no autorizador todas as requisições que não tem a anotação
		// "Publico"
		// System.out.println("Verificando aceitação: "
		// + controllerMethod.getClass().getName());
		// boolean retorno =
		// !controllerMethod.containsAnnotation(Publico.class);
		// System.out.println(retorno);
		//
		// this.urlAcessada = (!retorno);

		return retorno;
	}

	@AroundCall
	public void intercepta(SimpleInterceptorStack stack) {

		System.out.println("Autorizador: "
				+ controllerMethod.getClass().getName());

		this.urlAcessada = true;

		if (!controllerMethod.containsAnnotation(Publico.class)) {
			System.out
					.println("=========================REQUEST===========================");

			System.out.println("URL de implantação: ");
			System.out.println("RequestURI: " + request.getRequestURI());
			System.out.println("RequestURL: " + request.getRequestURL());

			System.out.println("Request.CharacterEncoding: "
					+ request.getCharacterEncoding());

			System.out.println("Parametros da requisição: ");
			Enumeration<String> parameterNames = request.getParameterNames();

			while (parameterNames.hasMoreElements()) {
				String paramName = parameterNames.nextElement();
				System.out.println("");
				System.out.println(paramName + ":");

				String[] paramValues = request.getParameterValues(paramName);
				for (int i = 0; i < paramValues.length; i++) {
					String paramValue = paramValues[i];
					System.out.print("  ");
					System.out.println(paramValue);
				}
			}

			System.out.println(" ");
			System.out.println("Usuario logado:");
			System.out.println(usuarioLogado.chkLogado());
			System.out.println(" ");

			if (!usuarioLogado.chkLogado()) {

				System.out.println("Usuário de implantação não está logado.");

				AuditoriaFactory af = new AuditoriaFactory();
				Auditoria auditoria = af.getAuditoria(this.getEntityManager());// ETipoAuditoria.AcessoNegado

				System.out.println("Criando Auditoria de Acesso Negado");

				try {
					auditoria.setAtendida(0);
					auditoria.setDataOcorrencia(Calendar.getInstance());
					auditoria.setDescricao("Acesso Negado");
					auditoria.setTipoAuditoria(ETipoAuditoria.AcessoNegado);
					auditoria
							.setUrlSolitada(request.getRequestURL().toString());
					auditoria.inserir();

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				usuarioLogado.setUrlNegada(request.getRequestURL().toString());

				result.redirectTo(LoginController.class).login();
				this.urlAcessada = false;

				return;
			} else {

				System.out.println("Usuário Está Logado.");

				// result.redirectTo(AssistController.class).inicio();
				this.urlAcessada = true;
				// return;
			}
		} else {
			System.out.println("Annotation @Publico.");

			System.out.println("URL de implantação: ");
			System.out.println("RequestURI: " + request.getRequestURI());
			System.out.println("RequestURL: " + request.getRequestURL());

			// result.redirectTo(AssistController.class).inicio();
			this.urlAcessada = true;
			// return;
		}

		if (!this.urlAcessada) {
			AuditoriaFactory af = new AuditoriaFactory();
			Auditoria auditoria = af.getAuditoria(this.getEntityManager());// ETipoAuditoria.AcessoNegado

			System.out.println("Criando Auditoria de Acesso Negado");

			try {
				auditoria.setAtendida(0);
				auditoria.setDataOcorrencia(Calendar.getInstance());
				auditoria.setDescricao("Acesso Negado");
				auditoria.setTipoAuditoria(ETipoAuditoria.AcessoNegado);
				auditoria.setUrlSolitada(request.getRequestURL().toString());
				if (usuarioLogado != null) {
					if (usuarioLogado.chkLogado()) {
						auditoria.setUsername(usuarioLogado.getUsuario()
								.getUsername());
					}
				}

				auditoria.inserir();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

		AuditoriaFactory af = new AuditoriaFactory();
		Auditoria auditoria = af.getAuditoria(this.getEntityManager());// ETipoAuditoria.AcessoNegado

		try {
			auditoria.setAtendida(1);
			auditoria.setDataOcorrencia(Calendar.getInstance());
			auditoria.setDescricao("Acesso");
			auditoria.setTipoAuditoria(ETipoAuditoria.Acesso);
			auditoria.setUrlSolitada(request.getRequestURL().toString());
			if (usuarioLogado != null) {
				if (usuarioLogado.chkLogado()) {
					auditoria.setUsername(usuarioLogado.getUsuario()
							.getUsername());
				}
			}

			auditoria.inserir();

		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
		}

		stack.next();

	}

	@BeforeCall
	public void beforeCall() {
		System.out.println("Criando entityManagerFactory");
		entityManagerFactory = Persistence
				.createEntityManagerFactory("postgreSQL");

		System.out.println("Criando entityManager");
		entityManager = entityManagerFactory.createEntityManager();

	}

	@AfterCall
	public void afterCall() {
		System.out.println("Fechando entityManager ");
		entityManager.close();

		System.out.println("Fechando entityManagerFactory");
		entityManagerFactory.close();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
