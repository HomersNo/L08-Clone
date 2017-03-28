
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;
import domain.Auditor;
import domain.Lessor;
import domain.Tenant;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	//Services
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int actorId) {

		ModelAndView result;
		Actor actor;

		actor = actorService.findOne(actorId);

		result = new ModelAndView("redirect:/welcome/index.do");

		if (actor instanceof Tenant) {
			result = new ModelAndView("redirect:/tenant/display.do?tenantId=" + actor.getId());
			result.addObject("tenant", actor);
			result.addObject("comments", actor.getComments());
			result.addObject("socialIdentitites", actor.getSocialIdentities());
		} else if (actor instanceof Lessor) {
			result = new ModelAndView("redirect:/lessor/display.do?lessorId=" + actor.getId());
			result.addObject("comments", actor.getComments());
			result.addObject("lessor", actor);
			result.addObject("socialIdentitites", actor.getSocialIdentities());
		} else if (actor instanceof Auditor) {
			result = new ModelAndView("redirect:/auditor/display.do?auditorId=" + actor.getId());
			result.addObject("auditor", actor);
			result.addObject("socialIdentitites", actor.getSocialIdentities());
		}
		return result;
	}
}
