package org.springframework.samples.petclinic.model

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validator;

//import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Chris Jones
 *         Simple tests adapted to use the Spock acceptance test framework.
 */
public class ValidatorSpockTests extends Specification {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
	
	// 1. validate against blank first name
	def "first name cannot be empty"() {
	  setup:
	  def person = new Person();
	  def validator = createValidator();
	  
	  when:
	  person.setFirstName("")
	  person.setLastName("smith")
      Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
	  
	  then:
      constraintViolations.size() == 1;
      ConstraintViolation<Person> violation = constraintViolations.iterator().next();
	  violation.getPropertyPath().toString().equals("firstName");
	  violation.getMessage().equals("may not be empty");
	}
	
	// 2. validate against null first name.
    def "first name cannot be null"() {
        setup:
        def person = new Person();
        def validator = createValidator();

        when:
        person.setFirstName(null)
        person.setLastName("smith")
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        then:
        constraintViolations.size() == 1;
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        violation.getPropertyPath().toString().equals("firstName");
        violation.getMessage().equals("may not be empty");
    }
	
	// 3. validate a against a valid first name (non-empty, non-null value).
    def "first name is set properly"() {
        setup:
        def person = new Person();
        def validator = createValidator();

        when:
        person.setFirstName("steve")
        person.setLastName("smith")
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        then:
        person.firstName.equals("steve")
    }

	// 4. validate against empty last name
    def "last name cannot be empty"() {
        setup:
        def person = new Person();
        def validator = createValidator();

        when:
        person.setFirstName("steve")
        person.setLastName("")
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        then:
        constraintViolations.size() == 1;
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        violation.getPropertyPath().toString().equals("lastName");
        violation.getMessage().equals("may not be empty");
    }

	// 5. validate against null last name
    def "last name cannot be null"() {
        setup:
        def person = new Person();
        def validator = createValidator();

        when:
        person.setFirstName("steve")
        person.setLastName(null)
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        then:
        constraintViolations.size() == 1;
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        violation.getPropertyPath().toString().equals("lastName");
        violation.getMessage().equals("may not be empty");
    }
	
	// 6. validate a against a valid last name (non-empty, non-null value).
    def "last name is set properly"() {
        setup:
        def person = new Person();
        def validator = createValidator();

        when:
        person.setFirstName("steve")
        person.setLastName("smith")

        then:
        person.lastName.equals("smith")
    }
}
