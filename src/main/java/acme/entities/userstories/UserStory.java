
package acme.entities.userstories;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes --------------------------------------------------------------------------------
	@NotBlank
	@Length(max = 76)
	String						title;

	@NotBlank
	@Length(max = 101)
	String						description;

	@NotNull
	@Positive
	Integer						estimatedCost;

	@NotBlank
	@Length(max = 101)
	String						acceptanceCriteria;

	Priority					priority;

	@URL
	String						link;

	// Relationships ----------------------------------------------------------
	/*
	 * @NotNull
	 * 
	 * @Valid
	 * 
	 * @ManyToOne()
	 * private Project project;
	 */
}