/**
 * 
 */
package leilaoDeCentavos.controller;

import leilaoDeCentavos.annotation.Publico;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;

/**
 * @author Tiago Ferezin ( Data: 11/08/2015 ); Funcionalidade da Classe:
 *
 */
@Controller
public class LoginController {

	@Publico
	@Get({ "/login", "/logar" })
	public void login() {

	}

}
