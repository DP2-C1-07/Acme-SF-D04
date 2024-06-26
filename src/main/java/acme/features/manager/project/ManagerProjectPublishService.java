
package acme.features.manager.project;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.userstories.UserStory;
import acme.features.manager.userstory.ManagerUserStoryRepository;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ManagerProjectRepository	managerProjectRepository;

	@Autowired
	private ManagerUserStoryRepository	managerUserStoryRepository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Manager manager;
		Project project;

		projectId = super.getRequest().getData("id", int.class);
		project = this.managerProjectRepository.findOneProjectById(projectId);

		Principal principal = super.getRequest().getPrincipal();
		manager = this.managerProjectRepository.findManagerById(principal.getActiveRoleId());

		status = project != null && super.getRequest().getPrincipal().hasRole(manager) && project.getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.managerProjectRepository.findOneProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "projectAbstract", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;
			existing = this.managerProjectRepository.findOneProjectByCode(object.getCode());
			super.state(existing == null || existing.getCode().equals(object.getCode()), "code", "manager.project.publish.error.duplicated");
		}

		Collection<UserStory> userStories = this.managerUserStoryRepository.findAllUserStoriesByProjectId(object.getId());

		boolean conditionUserStory = !userStories.isEmpty() && userStories.stream().noneMatch(u -> u.isDraftMode());
		super.state(conditionUserStory, "*", "manager.project.publish.error.draft-mode");

		boolean conditionProject = object.isDraftMode();
		super.state(conditionProject, "*", "manager.project.publish.error.draft-mode");

	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		object.setDraftMode(false);
		this.managerProjectRepository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "projectAbstract", "indication", "cost", "link", "manager");
		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}
}
