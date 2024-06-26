
package acme.features.administrator.risk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.risk.Risk;

@Controller
public class AdministratorRiskController extends AbstractController<Administrator, Risk> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorRiskListService		listService;

	@Autowired
	protected AdministratorRiskShowService		showService;

	@Autowired
	protected AdministratorRiskCreateService	createService;

	@Autowired
	protected AdministratorRiskUpdateService	updateService;

	@Autowired
	protected AdministratorRiskDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
