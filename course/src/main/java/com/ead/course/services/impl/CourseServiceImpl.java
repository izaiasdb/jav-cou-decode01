package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.domain.models.*;
import com.ead.course.dtos.NotificationCommandDto;
import com.ead.course.publishers.NotificationCommandPublisher;
import com.ead.course.repositories.*;
import com.ead.course.services.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    CourseUserRepository courseUserRepository;

    @Autowired
    NotificationCommandPublisher notificationCommandPublisher;

    @Autowired
    AuthUserClient authUserClient;

//     V1
//    @Transactional
//    @Override
//    public void delete(CourseModel courseModel) {
//        List<ModuleModel> moduleModelList = moduleRepository.findAllLModulesIntoCourse(courseModel.getCourseId());
//        if (!moduleModelList.isEmpty()){
//            for(ModuleModel module : moduleModelList){
//                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
//                if (!lessonModelList.isEmpty()){
//                    lessonRepository.deleteAll(lessonModelList);
//                }
//            }
//            moduleRepository.deleteAll(moduleModelList);
//        }
//        courseRepository.deleteCourseUserByCourse(courseModel.getCourseId());
//        courseRepository.delete(courseModel);
//    }

//    V2
//    @Transactional
//    @Override
//    public void delete(CourseModel courseModel) {
//        List<ModuleModel> moduleModelList = moduleRepository.findAllLModulesIntoCourse(courseModel.getCourseId());
//        if (!moduleModelList.isEmpty()){
//            for(ModuleModel module : moduleModelList){
//                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
//                if (!lessonModelList.isEmpty()){
//                    lessonRepository.deleteAll(lessonModelList);
//                }
//            }
//            moduleRepository.deleteAll(moduleModelList);
//        }
//        List<CourseUserModel> courseUserModelList = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
//        if (!courseUserModelList.isEmpty()) {
//            courseUserRepository.deleteAll(courseUserModelList);
//        }
//        courseRepository.delete(courseModel);
//    }

//  V3
//    @Transactional
//    @Override
//    public void delete(CourseModel courseModel) {
//        boolean deleteCourseUserInAuthUser = false;
//        List<ModuleModel> moduleModelList = moduleRepository.findAllLModulesIntoCourse(courseModel.getCourseId());
//        if (!moduleModelList.isEmpty()){
//            for(ModuleModel module : moduleModelList){
//                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
//                if (!lessonModelList.isEmpty()){
//                    lessonRepository.deleteAll(lessonModelList);
//                }
//            }
//            moduleRepository.deleteAll(moduleModelList);
//        }
//        List<CourseUserModel> courseUserModelList = courseUserRepository.findAllCourseUserIntoCourse(courseModel.getCourseId());
//        if(!courseUserModelList.isEmpty()){
//            courseUserRepository.deleteAll(courseUserModelList);
//            deleteCourseUserInAuthUser = true;
//        }
//        courseRepository.delete(courseModel);
//        if(deleteCourseUserInAuthUser){
//            authUserClient.deleteCourseInAuthUser(courseModel.getCourseId());
//        }
//    }

//    V4
    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelList = moduleRepository.findAllLModulesIntoCourse(courseModel.getCourseId());
        if (!moduleModelList.isEmpty()){
            for(ModuleModel module : moduleModelList){
                List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessonModelList.isEmpty()){
                    lessonRepository.deleteAll(lessonModelList);
                }
            }
            moduleRepository.deleteAll(moduleModelList);
        }
        courseRepository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return courseRepository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

//    @Override
//    public List<CourseModel> findAll() {
//        return courseRepository.findAll();
//    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        return courseRepository.existsByCourseAndUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        courseRepository.saveCourseUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourseAndSendNotification(CourseModel course, UserModel user) {
        courseRepository.saveCourseUser(course.getCourseId(), user.getUserId());
        try {
            var notificationCommandDto = new NotificationCommandDto();
            notificationCommandDto.setTitle("Bem-Vindo(a) ao Curso: " + course.getName());
            notificationCommandDto.setMessage(user.getFullName() + " a sua inscrição foi realizada com sucesso!");
            notificationCommandDto.setUserId(user.getUserId());
            notificationCommandPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e) {
            log.warn("Error sending notification!");
        }
    }


}
