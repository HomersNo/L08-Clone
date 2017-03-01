
package controllers.lessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.LessorService;
import controllers.AbstractController;

@Controller
@RequestMapping("/lessor/lessor")
public class LessorLessorController extends AbstractController {

	//Services

	@Autowired
	private LessorService	lessorService;


	// Constructor

	public LessorLessorController() {
		super();
	}

	//Methods

}
