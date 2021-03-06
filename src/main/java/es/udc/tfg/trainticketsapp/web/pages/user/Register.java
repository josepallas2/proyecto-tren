package es.udc.tfg.trainticketsapp.web.pages.user;

import java.util.Calendar;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;
import es.udc.tfg.trainticketsapp.model.userservice.UserProfileDetails;
import es.udc.tfg.trainticketsapp.model.userservice.UserService;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Register {

	@Property
	private String loginName;

	@Property
	private String password;

	@Property
	private String retypePassword;

	@Property
	private String firstName;

	@Property
	private String lastName;

	@Property
	private String dni;

	@Property
	private Calendar birthdate;

	@Property
	private String email;

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;

	@Component
	private Form registrationForm;

	@Component(id = "loginName")
	private TextField loginNameField;

	@Component(id = "password")
	private PasswordField passwordField;

	@Inject
	private Messages messages;

	private Long userProfileId;

	void onValidateFromRegistrationForm() {

		if (!registrationForm.isValid()) {
			return;
		}

		if (!password.equals(retypePassword)) {
			registrationForm.recordError(passwordField,
					messages.get("error-passwordsDontMatch"));
		} else {

			try {
				UserProfile userProfile = userService.registerUser(loginName,
						password, new UserProfileDetails(firstName, lastName,
								email, dni, Calendar.getInstance(),
								UserProfile.TypeUser.CLIENT));
				userProfileId = userProfile.getUserProfileId();
			} catch (DuplicateInstanceException e) {
				registrationForm.recordError(loginNameField,
						messages.get("error-loginNameAlreadyExists"));
			}

		}

	}

	Object onSuccess() {

		userSession = new UserSession();
		userSession.setUserProfileId(userProfileId);
		userSession.setFirstName(firstName);
		return Index.class;

	}

}
