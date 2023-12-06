package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
//import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.models.UserModel;
//import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    UserCourseRepository userCourseRepository;

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserEventPublisher userEventPublisher;

//    @Override
//    public List<UserModel> findAll() {
//        return userRepository.findAll();
//    }

//    @Override
//    public Page<UserModel> findAll(Pageable pageable) {
//        return userRepository.findAll(pageable);
//    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

//    V1
//    @Override
//    public void delete(UserModel userModel) {
//        List<UserCourseModel> userCourseModelList = userCourseRepository.findAllCourseUserIntoCourse(userModel.getUserId());
//
//        if (!userCourseModelList.isEmpty()) {
//            userCourseRepository.deleteAll(userCourseModelList);
//        }
//
//        userRepository.delete(userModel);
//    }

//    V2
//    @Transactional
//    @Override
//    public void delete(UserModel userModel) {
//        boolean deleteUserCourseInCourse = false;
//        List<UserCourseModel> userCourseModelList = userCourseRepository.findAllUserCourseIntoUser(userModel.getUserId());
//        if(!userCourseModelList.isEmpty()){
//            userCourseRepository.deleteAll(userCourseModelList);
//            deleteUserCourseInCourse = true;
//        }
//        userRepository.delete(userModel);
//        if(deleteUserCourseInCourse){
//            courseClient.deleteUserInCourse(userModel.getUserId());
//        }
//    }

    // V3
    @Transactional
    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public UserModel saveUser(UserModel userModel){
        userModel = save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
        return userModel;
    }


    @Transactional
    @Override
    public void deleteUser(UserModel userModel) {
        delete(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.DELETE);
    }

    @Transactional
    @Override
    public UserModel updateUser(UserModel userModel) {
        userModel = save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.UPDATE);
        return userModel;
    }

}
