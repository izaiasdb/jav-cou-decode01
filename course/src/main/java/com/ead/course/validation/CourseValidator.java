package com.ead.course.validation;


//import com.ead.course.configs.security.AuthenticationCurrentUserService;
import com.ead.course.clients.AuthUserClient;
import com.ead.course.domain.enums.UserType;
import com.ead.course.domain.models.UserModel;
import com.ead.course.dtos.CourseDto;
//import com.ead.course.dtos.UserDto;
import com.ead.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    UserService userService;

    @Autowired
    AuthUserClient authUserClient;

//    @Autowired
//    AuthenticationCurrentUserService authenticationCurrentUserService;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

//    V1
//    @Override
//    public void validate(Object o, Errors errors) {
//        CourseDto courseDto = (CourseDto) o;
//        validator.validate(courseDto, errors);
//        if(!errors.hasErrors()){
//            validateUserInstructor(courseDto.getUserInstructor(), errors);
//        }
//    }

//    V2
    @Override
    public void validate(Object o, Errors errors) {
        CourseDto courseDto = (CourseDto) o;
        validator.validate(courseDto, errors);
        if(!errors.hasErrors()){
//            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

//    V1
//    private void validateUserInstructor(UUID userInstructor, Errors errors){
//        ResponseEntity<UserDto> responseInstructor;
//
//        try {
//            responseInstructor = authUserClient.getOneUser(userInstructor);
//
//            if(responseInstructor.getBody().getUserType().equals(UserType.STUDENT)) {
//                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
//            }
//        } catch (HttpStatusCodeException e) {
//            //e.printStackTrace();
//            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
//                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
//            }
//        }
//    }

//    private void validateUserInstructor(UUID userInstructor, Errors errors){
//        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();
//        if(currentUserId.equals(userInstructor)) {
//            Optional<UserModel> userModelOptional = userService.findById(userInstructor);
//            if (!userModelOptional.isPresent()) {
//                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
//            }
//            if (userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())) {
//                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN.");
//            }
//        } else {
//            throw new AccessDeniedException("Forbidden");
//        }
//    }
}
