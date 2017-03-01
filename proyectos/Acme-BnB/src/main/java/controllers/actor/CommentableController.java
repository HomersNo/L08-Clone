
package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.CommentableService;
import controllers.AbstractController;

@Controller
@RequestMapping("/commentable/actor")
public class CommentableController extends AbstractController {

	// Services -----------------------------------------------------------------------
	@Autowired
	private CommentableService	commentableService;


	// Constructor --------------------------------------------------------------------
	public CommentableController() {
		super();
	}

	// Listing ------------------------------------------------------------------------

	// Creation -----------------------------------------------------------------------

	// Edition ------------------------------------------------------------------------

	// Ancillary methods --------------------------------------------------------------

}
