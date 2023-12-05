package com.ead.course.services;

import com.ead.course.domain.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    CourseUserModel save(CourseUserModel courseUserModel);
    CourseUserModel saveSubscriptionUserInCourse(CourseUserModel courseUserModel);

    boolean existsByUserId(UUID userId);

    void deleteCourseUserByUser(UUID userId);
}
