package com.ead.course.controllers;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.domain.enums.UserStatus;
import com.ead.course.domain.models.CourseModel;
//import com.ead.course.domain.models.CourseUserModel;
import com.ead.course.domain.models.UserModel;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.CourseService;
//import com.ead.course.services.CourseUserService;
import com.ead.course.services.UserService;
import com.ead.course.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    @Autowired
    AuthUserClient authUserClient;

    @Autowired
    CourseService courseService;

//    @Autowired
//    CourseUserService courseUserService;

    @Autowired
    UserService userService;

//    @GetMapping("/courses/{courseId}/users")
//    public ResponseEntity<Object> getAllUsersByCourse(
//            SpecificationTemplate.UserSpec spec,
//            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
//            @PathVariable(value = "courseId") UUID courseId){
//        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
//        if(!courseModelOptional.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("");
//    }

//    V1
//    @GetMapping("/courses/{courseId}/users")
//    public ResponseEntity<Object> getAllUsersByCourse(
//            SpecificationTemplate.UserSpec spec,
//            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
//            @PathVariable(value = "courseId") UUID courseId){
//        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
//        if(!courseModelOptional.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllCoursesByUser(courseId, pageable, null));
//    }

//    V3
//    @GetMapping("/courses/{courseId}/users")
//    public ResponseEntity<Page<UserDto>> getAllUsersByCourse(
//            SpecificationTemplate.UserSpec spec,
//            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
//            @PathVariable(value = "courseId") UUID courseId){
//        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
//        if(!courseModelOptional.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
//    }

    //V4
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getAllUsersByCourse(
            SpecificationTemplate.UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "courseId") UUID courseId){
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(!courseModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable));
    }

//    V1
//    @PostMapping("/courses/{courseId}/users/subscription")
//    public  ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
//                                                                @RequestBody @Valid SubscriptionDto subscriptionDto){
//        ResponseEntity<UserDto> responseUser = null;
//        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
//        if(!courseModelOptional.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
//        }
//        if(courseService.existsByCourseAndUser(courseId, subscriptionDto.getUserId())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
//        }
//        try {
//            responseUser = authUserClient.getOneUser(subscriptionDto.getUserId());
//        } catch (HttpStatusCodeException e) {
//            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
//            }
//        }

//        CourseUserModel courseUserModel = courseUserService.save(courseModelOptional.get()
//                .convertToCourseUserModel(responseUser.getBody().getUserId()));
//        CourseUserModel courseUserModel = courseUserService.saveSubscriptionUserInCourse(courseModelOptional.get()
//                .convertToCourseUserModel(responseUser.getBody().getUserId()));
//        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserModel);
//    }

//    V2
    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/courses/{courseId}/users/subscription")
    public  ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                                @RequestBody @Valid SubscriptionDto subscriptionDto){
        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
        if(!courseModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }
        if(courseService.existsByCourseAndUser(courseId, subscriptionDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        Optional<UserModel> userModelOptional = userService.findById(subscriptionDto.getUserId());
        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        if(userModelOptional.get().getUserStatus().equals(UserStatus.BLOCKED.toString())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
        }
        courseService.saveSubscriptionUserInCourseAndSendNotification(courseModelOptional.get(), userModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }

//    @DeleteMapping("/courses/users/{userId}")
//    public ResponseEntity<Object> deleteCourseUserByUser(@PathVariable(value = "userId") UUID userId){
//        if(!courseUserService.existsByUserId(userId)){
//            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("CourseUser not found.");
//        }
//        courseUserService.deleteCourseUserByUser(userId);
//        return  ResponseEntity.status(HttpStatus.OK).body("CourseUser deleted successfully.");
//    }
}
