package com.murali.school.ui.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.murali.school.models.OAuthClientDetails;
import com.murali.school.models.Student;
import com.murali.school.models.Teacher;
import com.murali.school.models.User;
import com.murali.school.ui.config.AccessToken;
import com.murali.school.ui.model.AuthUserDetail;
import com.murali.school.ui.repo.OAuthClientCreateRepo;
import com.murali.school.ui.repo.UserCreateRepo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UIServiceImpl implements UIService, UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(UIServiceImpl.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserCreateRepo repo;
	
	@Autowired
	OAuthClientCreateRepo oauthClientRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername username=" + username);
		logger.info("======== {} ========", SecurityContextHolder.getContext().getAuthentication());

		Optional<User> optionalUser = repo.findByusername(username);

		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

		UserDetails userDetails = new AuthUserDetail(optionalUser.get());
		new AccountStatusUserDetailsChecker().check(userDetails);
		return userDetails;
	}

	@Override
	public User save(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return this.repo.save(user);
	}

	@Override
	public Optional<User> findUserByusername(String username) {
		return this.repo.findByusername(username);
	}

	@Override
	public Teacher searchTeacher(Teacher teacher) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Teacher> teacherHttpEntity = new HttpEntity<>(httpHeaders);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", teacher.getName());
		return getTeacher(teacherHttpEntity, params);
	}

	@HystrixCommand(fallbackMethod = "searchTeacher_fallback", commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false"),
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })

	private Teacher getTeacher(HttpEntity<Teacher> teacherHttpEntity, Map<String, String> params) {
		Teacher teacherFromDb = new Teacher();

		try {
			ResponseEntity<Teacher> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/teacherservice/api/teacher/{name}", HttpMethod.GET, teacherHttpEntity,
					Teacher.class, params);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				teacherFromDb = responseEntity.getBody();
				teacherFromDb.setSuccessMessage("Teacher details found..");
			} else {
				teacherFromDb.setErrorMessage("There is no Teachr found for the given search criteria..");
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				teacherFromDb.setErrorMessage("The resource you are requested is not found");
			} else if (e.getRawStatusCode() == 401) {
				teacherFromDb.setErrorMessage("You are not authorized to do this action");

			} else if (e.getRawStatusCode() == 500) {
				teacherFromDb.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return teacherFromDb;
	}

	public Teacher searchTeacher_fallback(Teacher teacher, Throwable exception) {
		logger.info("searchTeacher fallback is invoked" + exception.getMessage());
		return new Teacher();
	}

	@Override
	public Student searchStudent(Student student) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Student> studentHttpEntity = new HttpEntity<>(httpHeaders);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", student.getName());
		student = getStudent(studentHttpEntity, params);
		return student;
	}

	@HystrixCommand(fallbackMethod = "searchStudent_fallback", commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false"),
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })

	private Student getStudent(HttpEntity<Student> studentHttpEntity, Map<String, String> params) {
		Student studentFromDb = new Student();

		try {
			ResponseEntity<Student> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/studentservice/api/student/{name}", HttpMethod.GET, studentHttpEntity,
					Student.class, params);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				studentFromDb = responseEntity.getBody();
				studentFromDb.setSuccessMessage("Student details found... ");
			} else {
				studentFromDb.setErrorMessage("There is no Student found for the given search criteria..");
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				studentFromDb.setErrorMessage("The resource you are requested is not found");
			} else if (e.getRawStatusCode() == 401) {
				studentFromDb.setErrorMessage("You are not authorized to do this action");

			} else if (e.getRawStatusCode() == 500) {
				studentFromDb.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return studentFromDb;
	}

	public Student searchStudent_fallback(Student student, Throwable exception) {
		logger.info("searchStudent fallback is invoked" + exception.getMessage());
		return new Student();
	}

	@Override
	public Student createStudent(Student student) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Student> studentHttpEntity = new HttpEntity<>(student, httpHeaders);
		return saveStudent(studentHttpEntity);
	}

	@HystrixCommand(fallbackMethod = "createStudent_fallback", commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false"),
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })

	private Student saveStudent(HttpEntity<Student> studentHttpEntity) {
		Student studentFromDB = new Student();
		try {
			ResponseEntity<Student> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/studentservice/api/student", HttpMethod.POST, studentHttpEntity,
					Student.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				studentFromDB = responseEntity.getBody();
				studentFromDB.setSuccessMessage("Student created successfully..");
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				studentFromDB.setErrorMessage("The resource you are requested is not found");
			} else if (e.getRawStatusCode() == 401) {
				studentFromDB.setErrorMessage("You are not authorized to do this action");

			} else if (e.getRawStatusCode() == 500) {
				studentFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return studentFromDB;
	}

	public Student createStudent_fallback(Student student, Throwable exception) {
		logger.info("createStudent fallback is invoked" + exception.getMessage());
		return new Student();
	}

	@Override
	public Teacher createTeacher(Teacher teacher) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Teacher> teacherHttpEntity = new HttpEntity<>(teacher, httpHeaders);
		saveTeacher(teacherHttpEntity);
		return saveTeacher(teacherHttpEntity);
	}

	@HystrixCommand(fallbackMethod = "createTeacher_fallback", commandProperties = {
			@HystrixProperty(name = "execution.timeout.enabled", value = "false"),
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })

	private Teacher saveTeacher(HttpEntity<Teacher> teacherHttpEntity) {
		Teacher teacherFromDB = new Teacher();
		try {
			ResponseEntity<Teacher> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/teacherservice/api/teacher", HttpMethod.POST, teacherHttpEntity,
					Teacher.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				teacherFromDB.setSuccessMessage("Teacher created successfully..");
				teacherFromDB = responseEntity.getBody();
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				teacherFromDB.setErrorMessage("The resource you are requested is not found");
			} else if (e.getRawStatusCode() == 401) {
				teacherFromDB.setErrorMessage("You are not authorized to do this action");

			} else if (e.getRawStatusCode() == 500) {
				teacherFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return teacherFromDB;
	}

	public Teacher createTeacher_fallback(Teacher teacher, Throwable exception) {
		logger.info("createStudent fallback is invoked" + exception.getMessage());
		return new Teacher();
	}

	@Override
	public Student updateStudent(Student student) {
		Student updatedFromDB = new Student();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Student> customerHttpEntity = new HttpEntity<>(student, httpHeaders);
		try {
			ResponseEntity<Student> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/studentservice/api/student", HttpMethod.PUT, customerHttpEntity,
					Student.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				updatedFromDB=responseEntity.getBody();
				updatedFromDB.setSuccessMessage("Student updated successfully..");
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				updatedFromDB.setErrorMessage("You are not authorized to do this action");
			} else if (e.getRawStatusCode() == 401) {
				updatedFromDB.setErrorMessage("The resource you are requested is not found");

			} else if (e.getRawStatusCode() == 500) {
				updatedFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return updatedFromDB;

	}

	@Override
	public Teacher updateTeacher(Teacher teacher) {
		Teacher updatedFromDB = new Teacher();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Teacher> customerHttpEntity = new HttpEntity<>(teacher, httpHeaders);
		try {
			ResponseEntity<Teacher> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/teacherservice/api/teacher", HttpMethod.PUT, customerHttpEntity,
					Teacher.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				updatedFromDB = responseEntity.getBody();
				updatedFromDB.setSuccessMessage("Teacher updated successfully..");

			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				updatedFromDB.setErrorMessage("You are not authorized to do this action");
			} else if (e.getRawStatusCode() == 401) {
				updatedFromDB.setErrorMessage("The resource you are requested is not found");

			} else if (e.getRawStatusCode() == 500) {
				updatedFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return updatedFromDB;

	}

	@Override
	public User updateUser(User user) {
		User userFromDB = new User();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<User> customerHttpEntity = new HttpEntity<>(user, httpHeaders);
		try {
			ResponseEntity<User> responseEntity = restTemplate.exchange("http://GATEWAYSERVICE/userservice/api/user",
					HttpMethod.PUT, customerHttpEntity, User.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				userFromDB = responseEntity.getBody();
				userFromDB.setSuccessMessage("User updated successfully..");
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				userFromDB.setErrorMessage("You are not authorized to do this action");
			} else if (e.getRawStatusCode() == 401) {
				userFromDB.setErrorMessage("The resource you are requested is not found");

			} else if (e.getRawStatusCode() == 500) {
				userFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return userFromDB;
	}

	@Override
	public Student findStudentByName(String name) {
		Student studentFromDB = new Student();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		HttpEntity<Student> customerHttpEntity = new HttpEntity<>(httpHeaders);
		try {
			ResponseEntity<Student> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/studentservice/api/student/{name}", HttpMethod.GET, customerHttpEntity,
					Student.class, params);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				studentFromDB = responseEntity.getBody();
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				studentFromDB.setErrorMessage("You are not authorized to do this action");
			} else if (e.getRawStatusCode() == 401) {
				studentFromDB.setErrorMessage("The resource you are requested is not found");

			} else if (e.getRawStatusCode() == 500) {
				studentFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return studentFromDB;
	}

	@Override
	public Teacher findTeacherByName(String name) {
		Teacher teacherFromDB = new Teacher();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Teacher> customerHttpEntity = new HttpEntity<>(httpHeaders);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		try {
			ResponseEntity<Teacher> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/teacherservice/api/teacher/{name}", HttpMethod.GET, customerHttpEntity,
					Teacher.class, params);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				teacherFromDB = responseEntity.getBody();
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				teacherFromDB.setErrorMessage("You are not authorized to do this action");
			} else if (e.getRawStatusCode() == 401) {
				teacherFromDB.setErrorMessage("The resource you are requested is not found");

			} else if (e.getRawStatusCode() == 500) {
				teacherFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return teacherFromDB;
	}

	@Override
	public Student findStudentById(int id) {
		Student studentFromDB = new Student();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", id);
		HttpEntity<Student> customerHttpEntity = new HttpEntity<>(httpHeaders);
		try {
			ResponseEntity<Student> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/studentservice/api/student/{id}", HttpMethod.GET, customerHttpEntity,
					Student.class, params);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				studentFromDB = responseEntity.getBody();
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				studentFromDB.setErrorMessage("You are not authorized to do this action");
			} else if (e.getRawStatusCode() == 401) {
				studentFromDB.setErrorMessage("The resource you are requested is not found");

			} else if (e.getRawStatusCode() == 500) {
				studentFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return studentFromDB;
	}

	@Override
	public Teacher findTeacherById(int id) {
		Teacher teacherFromDB = new Teacher();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Teacher> customerHttpEntity = new HttpEntity<>(httpHeaders);
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", id);
		try {
			ResponseEntity<Teacher> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/teacherservice/api/teacher/{id}", HttpMethod.GET, customerHttpEntity,
					Teacher.class, params);
			if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody()!=null) {
				teacherFromDB = responseEntity.getBody();
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			if (e.getRawStatusCode() == 403) {
				teacherFromDB.setErrorMessage("You are not authorized to do this action");
			} else if (e.getRawStatusCode() == 401) {
				teacherFromDB.setErrorMessage("The resource you are requested is not found");

			} else if (e.getRawStatusCode() == 500) {
				teacherFromDB.setErrorMessage("Error caught during processing of the request, please try again");

			}

		}
		return teacherFromDB;
	}

	@Override
	public Optional<User> findUserById(int id) {
		return this.repo.findById(id);
	}

	@Override
	public boolean deleteUser(User user) {
		try {
			this.repo.delete(user);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean deleteTeacher(Teacher teacher) {
		boolean deleted = false;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Teacher> teacherHttpEntity = new HttpEntity<>(teacher, httpHeaders);
		try {
			ResponseEntity<Teacher> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/teacherservice/api/teacher/", HttpMethod.DELETE, teacherHttpEntity,
					Teacher.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				deleted = true;
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			deleted = false;

		}
		return deleted;

	}

	@Override
	public boolean deleteStudent(Student student) {
		boolean deleted = false;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + AccessToken.getAccessToken());
		HttpEntity<Student> studentHttpEntity = new HttpEntity<>(student, httpHeaders);
		try {
			ResponseEntity<Student> responseEntity = restTemplate.exchange(
					"http://GATEWAYSERVICE/studentservice/api/student", HttpMethod.DELETE, studentHttpEntity,
					Student.class);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				deleted = true;
			}

		} catch (HttpStatusCodeException e) {
			ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
					.body(e.getResponseBodyAsString());
			deleted = false;

		}
		return deleted;
	}

	@Override
	public OAuthClientDetails saveOAuthClient(OAuthClientDetails oauthClient) {
		String secret = passwordEncoder.encode(oauthClient.getClient_secret());
		oauthClient.setClient_secret(secret);		
		return oauthClientRepo.save(oauthClient);
	}

}
