package com.murali.school.ui.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.murali.school.constans.Permissions;
import com.murali.school.constans.RoleType;
import com.murali.school.constans.TeacherType;
import com.murali.school.models.OAuthClientDetails;
import com.murali.school.models.Role;
import com.murali.school.models.Student;
import com.murali.school.models.Subjects;
import com.murali.school.models.Teacher;
import com.murali.school.models.User;
import com.murali.school.ui.service.UIService;

@Controller
public class UserHomeController {

	@Autowired
	private UIService uiService;

	@GetMapping("/common")
	public String showSecureHomePage(ModelMap model, Principal p) {
		System.out.println(p.getClass().getName());
		return "home";
	}

	@GetMapping("/")
	public String showHomePage(ModelMap model) {
		return "SMSHomePage";
	}

	@GetMapping("/login")
	public String showLoginPage(ModelMap model, String error, String logout, String sessionExpired) {
		User user = new User();
		if (error != null)
			user.setErrorMessage("Your username and password are invalid.");
		if (logout != null)
			user.setSuccessMessage("You have been logged out successfully.");
		if (sessionExpired != null)
			user.setErrorMessage("Your session is expired, please login again");
		model.put("user", user);
		return "login";
	}

	@GetMapping("/registerPage")
	public String showRegisterPage(ModelMap model) {
		User user = new User();
		model.put("user", user);
		loadData(model);
		return "register";
	}

	@PostMapping("/register")
	public String showWelcomePage(@ModelAttribute("user") User user, ModelMap model) {

		loadData(model);

		if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
			user.setErrorMessage("User Name or Password should not be blank");
			model.put("user", user);
			return "register";
		}

		if (user.getuIRoles().length == 0) {
			user.setErrorMessage("Atleast one role must be selected");
			model.put("user", user);
			return "register";
		}

		setRoles(user);

		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		User savedUser = this.uiService.save(user);
		if (savedUser.getId() != null) {
			savedUser.setSuccessMessage("User: " + savedUser.getUsername() + "  created successfully..");
			model.put("user", savedUser);
		}

		return "register";
	}

	public void loadData(ModelMap model) {
		List<String> rolesList = new ArrayList<>();
		rolesList.add(RoleType.ADMIN.toString());
		rolesList.add(RoleType.STUDENT.toString());
		rolesList.add(RoleType.TEACHER.toString());
		rolesList.add(RoleType.WORKER.toString());
		model.put("rolesList", rolesList);

		List<String> permissionList = new ArrayList<>();
		permissionList.add(Permissions.CREATE_PROFILE.toString());
		permissionList.add(Permissions.UPDATE_PROFILE.toString());
		permissionList.add(Permissions.DELETE_PROFILE.toString());
		permissionList.add(Permissions.READ_PROFILE.toString());
		model.put("permissionList", rolesList);

		List<String> subjects = new ArrayList<>();
		subjects.add("TELUGU");
		subjects.add("HINDI");
		subjects.add("ENGLISH");
		subjects.add("MATHS");
		subjects.add("SCIENCE");
		subjects.add("SOCIAL");
		model.put("subjects", subjects);

		List<String> teacherTypes = new ArrayList<>();
		teacherTypes.add(TeacherType.PERMENANT.toString());
		teacherTypes.add(TeacherType.TEMP.toString());

		model.put("teacherTypes", teacherTypes);

	}

	@GetMapping("/searchUserPage")
	public String searchUserPage(@ModelAttribute("user") User user, ModelMap model) {
		return "searchUser";
	}

	@GetMapping("/searchTeacherPage")
	public String searchTeacherPage(@ModelAttribute("teacher") Teacher teacher, ModelMap model) {
		return "searchTeacher";
	}

	@GetMapping("/searchStudentPage")
	public String searchStudentPage(@ModelAttribute("student") Student student, ModelMap model) {
		return "searchStudent";
	}

	@GetMapping("/createTeacherPage")
	public String createTeacherPage(ModelMap model) {
		Teacher teacher = new Teacher();
		loadData(model);
		model.put("teacher", teacher);
		return "createTeacher";
	}

	@GetMapping("/createStudentPage")
	public String createStudentPage(ModelMap model) {
		Student student = new Student();
		loadData(model);
		model.put("student", student);
		return "createStudent";
	}

	@PostMapping("/createTeacher")
	public String createTeacher(@ModelAttribute("teacher") Teacher teacher, ModelMap model) {
		if (teacher.getName().isEmpty() || teacher.getuISubjects().length < 0) {
			teacher.setErrorMessage("Teacher Name or Subjects should not be blank");
			model.put("teacher", teacher);
			return "createTeacher";
		}

		setSubjects(teacher);
		teacher = this.uiService.createTeacher(teacher);
		setUISubjects(teacher);
		loadData(model);
		model.put("teacher", teacher);
		return "createTeacher";
	}

	@PostMapping("/createStudent")
	public String createStudent(@ModelAttribute("student") Student student, ModelMap model) {
		if (student.getName().isEmpty() || student.getStudentClass().isEmpty() || student.getAge() == 0) {
			student.setErrorMessage("Teacher Name or Subjects should not be blank");
			model.put("student", student);
			return "createStudent";
		}
		student = this.uiService.createStudent(student);
		loadData(model);
		model.put("student", student);
		return "createStudent";
	}

	@PostMapping("/searchUser")
	public String searchUser(@ModelAttribute("user") User user, ModelMap model) {
		User modelUser = new User();
		if (user.getUsername() != null) {
			Optional<User> searchedUser = this.uiService.findUserByusername(user.getUsername());
			if (searchedUser.isPresent()) {
				modelUser = searchedUser.get();
				modelUser.setSuccessMessage("User details found..");
			} else {
				modelUser.setErrorMessage("There is no user found for the given search criteria..");
			}
		} else {
			modelUser.setErrorMessage("Please enter the search criteria..");
		}
		model.put("user", modelUser);
		return "searchUser";
	}

	@PostMapping("/searchStudent")
	public String searchStudent(@ModelAttribute("student") Student student, ModelMap model) {
		Student studentFromDb = new Student();
		if (!student.getName().isEmpty()) {
			studentFromDb = this.uiService.searchStudent(student);
		} else {
			studentFromDb.setErrorMessage("Please enter the search criteria..");
		}
		loadData(model);
		model.put("student", studentFromDb);
		return "searchStudent";
	}

	@PostMapping("/searchTeacher")
	public String searchTeacher(@ModelAttribute("teacher") Teacher teacher, ModelMap model) {
		Teacher teacherFromDb = new Teacher();
		if (!teacher.getName().isEmpty()) {
			teacherFromDb = this.uiService.searchTeacher(teacher);
		} else {
			teacherFromDb.setErrorMessage("Please enter the search criteria..");
		}
		loadData(model);
		model.put("teacher", teacherFromDb);
		return "searchTeacher";
	}

	@GetMapping("/updateUserPage/{username}")
	public String updateUserPage(@PathVariable("username") String username, ModelMap model) {
		loadData(model);
		if (username != null) {
			Optional<User> searchedUser = this.uiService.findUserByusername(username);
			if (searchedUser.isPresent()) {
				User user = searchedUser.get();
				setUIRoles(user);
				model.put("user", searchedUser.get());
			}
		}
		return "updateUser";
	}

	@GetMapping("/updateTeacherPage/{name}")
	public String updateTeacherPage(@PathVariable("name") String name, ModelMap model) {
		loadData(model);
		if (name != null) {
			Teacher searchedUser = this.uiService.findTeacherByName(name);
			setUISubjects(searchedUser);
			model.put("teacher", searchedUser);

		}
		return "updateTeacher";
	}
	
	@PostMapping("/registerOAuthClient")
	public String registerOAuthClient(@ModelAttribute("oauthClient") OAuthClientDetails oauthClient, ModelMap model) {
		loadData(model);
		if (oauthClient != null) {
			OAuthClientDetails searchedUser = this.uiService.saveOAuthClient(oauthClient);
			searchedUser.setSuccessMessage("OAUTH Client is registered successfully..");
			model.put("oauthClient", searchedUser);

		}
		return "RegisterClient";
	}	
	
	@GetMapping("/registerOAuthClientPage")
	public String registerOAuthClientPage(ModelMap model) {
		OAuthClientDetails oauthClient=new OAuthClientDetails();
		model.put("oauthClient", oauthClient);
		return "RegisterClient";
	}	
	

	@GetMapping("/updateStudentPage/{name}")
	public String updateStudentPage(@PathVariable("name") String name, ModelMap model) {
		loadData(model);
		if (name != null) {
			Student searchedUser = this.uiService.findStudentByName(name);
			model.put("student", searchedUser);
		}
		return "updateStudent";
	}

	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute("user") User user, ModelMap model) throws IOException {
		setRoles(user);
		user = this.uiService.updateUser(user);
		loadData(model);
		setUIRoles(user);
		model.put("user", user);
		return "updateUser";
	}

	@PostMapping("/updateTeacher")
	public String updateTeacher(@ModelAttribute("teacher") Teacher teacher, ModelMap model) throws IOException {
		setSubjects(teacher);
		teacher = this.uiService.updateTeacher(teacher);
		loadData(model);
		setUISubjects(teacher);
		model.put("teacher", teacher);
		return "updateTeacher";
	}

	@PostMapping("/updateStudent")
	public String updateStudent(@ModelAttribute("student") Student student, ModelMap model) throws IOException {
		student = this.uiService.updateStudent(student);
		loadData(model);
		model.put("student", student);
		return "updateStudent";
	}

	private void setUISubjects(Teacher teacher) {
		if (teacher.getSubjects() != null && !teacher.getSubjects().isEmpty()) {
			String[] subjects = new String[teacher.getSubjects().size()];
			int index = 0;
			for (Subjects subject : teacher.getSubjects()) {
				subjects[index] = subject.getSubjectName();
				index++;
			}

			teacher.setuISubjects(subjects);
		}
	}

	private void setUIRoles(User user) {
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			String[] roles = new String[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roles[index] = role.getName();
				index++;
			}

			user.setuIRoles(roles);
		}
	}

	@GetMapping("/deleteTeacherPage/{id}")
	public String deleteTeacherPage(@PathVariable("id") int id, ModelMap model) {
		loadData(model);
		if (id > 0) {
			Teacher searchedUser = this.uiService.findTeacherById(id);
			boolean deleted = this.uiService.deleteTeacher(searchedUser);
			if (deleted) {
				searchedUser.setSuccessMessage("Teacher:" + searchedUser.getName() + " deleted successfully..");
			} else {
				searchedUser.setErrorMessage("Exception occured while deleteing the teacher..,Please try again.");
			}
			model.put("teacher", searchedUser);

		}
		return "searchTeacher";
	}

	@GetMapping("/deleteStudentPage/{id}")
	public String deleteStudentPage(@PathVariable("id") int id, ModelMap model) {
		loadData(model);
		if (id > 0) {
			Student student = this.uiService.findStudentById(id);
			boolean deleted = this.uiService.deleteStudent(student);
			if (deleted) {
				student.setSuccessMessage("Student:" + student.getName() + " deleted successfully..");
			} else {
				student.setErrorMessage("Exception occured while deleteing the student..,Please try again.");
			}
			model.put("student", student);

		}
		return "searchStudent";
	}

	@GetMapping("/deleteUserPage/{id}")
	public String deleteUserPage(@PathVariable("id") int id, ModelMap model) {
		loadData(model);
		User user = null;
		if (id > 0) {
			Optional<User> searchedUser = this.uiService.findUserById(id);
			if (searchedUser.isPresent()) {
				user = searchedUser.get();
				boolean deleted = this.uiService.deleteUser(user);
				if (deleted) {
					user.setSuccessMessage("User:" + user.getUsername() + " deleted successfully..");
				} else {
					user.setErrorMessage("Exception occured while deleteing the user..,Please try again.");
				}
			}
			model.put("user", user);

		}
		return "searchUser";
	}

	private void setSubjects(Teacher teacher) {
		if (teacher.getuISubjects() != null && teacher.getuISubjects().length > 0) {
			for (String subjectName : teacher.getuISubjects()) {
				Subjects subject = new Subjects();
				subject.setSubjectName(subjectName);				
				teacher.getSubjects().add(subject);
			}
			
		}

	}

	private void setRoles(User user) {
		List<Role> rolesList = new ArrayList<Role>();
		if (user.getuIRoles() != null && user.getuIRoles().length > 0) {
			for (String roleName : user.getuIRoles()) {
				Role aRole = new Role();
				aRole.setName(roleName);
				aRole.setPermissions(null);
				rolesList.add(aRole);
			}
			user.setRoles(rolesList);
		}
	}

}
