package com.ead.course.services;

import com.ead.course.domain.models.CourseUserModel;

public interface CourseUserService {
    CourseUserModel save(CourseUserModel courseUserModel);
    CourseUserModel saveSubscriptionUserInCourse(CourseUserModel courseUserModel);
}
