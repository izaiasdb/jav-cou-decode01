package com.ead.course.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

//@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@Entity
//@Table(name = "TB_COURSES_USERS")
//public class CourseUserModel implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_id")
//    private CourseModel course;
//
//    @Column(nullable = false)
//    private UUID userId;
//}
