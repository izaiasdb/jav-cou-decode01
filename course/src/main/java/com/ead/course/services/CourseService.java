package com.ead.course.services;

import com.ead.course.domain.models.CourseModel;
import com.ead.course.domain.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    void delete(CourseModel courseModel);

    CourseModel save(CourseModel courseModel);

    Optional<CourseModel> findById(UUID courseId);

    Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable);

//    List<CourseModel> findAll();

    boolean existsByCourseAndUser(UUID courseId, UUID userId);

    void saveSubscriptionUserInCourse(UUID courseId, UUID userId);

    void saveSubscriptionUserInCourseAndSendNotification(CourseModel course, UserModel user);
}
