# lab_scheduler 开头由客户端程序修改，其他表仅admin能修改

set foreign_key_checks = false;

DROP TABLE IF EXISTS course;

create table course
(
    id           int unsigned auto_increment primary key,
    course_code  char(5)      not null comment '课程代号',
    course_name  varchar(40)  not null,
    course_hour  varchar(3)   not null comment '学时',
    major_id     int unsigned not null comment '课程所属专业，不同专业可能学相同课程',
    school_id    int unsigned not null comment '课程所属学院，不同学院可能有相同课程',
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    unique (course_code, major_id, school_id),
    foreign key (school_id) references school (id)
);

DROP TABLE IF EXISTS teacher;

create table teacher
(
    id           int unsigned auto_increment primary key,
    teacher_id   char(5)      not null comment '教师工号',
    teacher_name varchar(20)  not null,
    school_id    int unsigned not null comment '所属学院id',
    password     char(32)     not null,
    limits       bool     default false comment '限制不可同时登录',
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    foreign key (school_id) references school (id)
);

DROP TABLE IF EXISTS teacher_course;

# 多个老师教多门课程
create table teacher_course
(
    id           int unsigned auto_increment primary key,
    teacher_id   int unsigned not null,
    course_id    int unsigned not null,
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    foreign key (teacher_id) REFERENCES teacher (id),
    foreign key (course_id) REFERENCES course (id)
);

DROP TABLE IF EXISTS school;

create table school
(
    id           int unsigned auto_increment primary key,
    school_name  varchar(40) not null,
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS lab;

create table lab
(
    id           int unsigned auto_increment primary key,
    lab_name     char(3)      not null,
    school_id    int unsigned not null,
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    unique (lab_name, school_id) comment '不同学院可能有相同的实验室名字',
    foreign key (school_id) references school (id)
);

DROP TABLE IF EXISTS lab_schedule;

CREATE TABLE lab_schedule
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    year         CHAR(4)              NOT NULL COMMENT '年份',
    semester     CHAR(1)              NOT NULL COMMENT '学期（1、2）',
    school_id    int unsigned         NOT NULL COMMENT '学院id',
    lab_id       int unsigned         NOT NULL COMMENT '实验室id',
    week         CHAR(2)              NOT NULL COMMENT '周次',
    session      CHAR(1)              NOT NULL COMMENT '第几次课，如1（12节）',
    weekday      CHAR(1)              NOT NULL COMMENT '星期几',
    major        int unsigned         not null comment '上课专业，如计算机（0405）',
    grade        int unsigned         not null comment '上课年级，如2021级',
#     class        int unsigned         not null comment '上课班级，如1、2班',
#     teacher_id   CHAR(5)              NOT NULL comment '上课的老师 可能有多个',
    `lock`       CHAR(1)  DEFAULT '1' NOT NULL COMMENT '0表示管理员加的课, 1表示可排课, 2表示已排课',
    course_id    int unsigned         not null comment '上课课程，如软件工程和数据库课程设计',
    note         VARCHAR(50)          NULL comment '备注',
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS lab_schedule_class;

# 多个班级上同一门课
CREATE TABLE lab_schedule_class
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    schedule_id  INT UNSIGNED NOT NULL comment '排课id',
    class_id     INT UNSIGNED NOT NULL comment '班级id',
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (schedule_id, class_id),
    FOREIGN KEY (schedule_id) REFERENCES lab_schedule (id),
    FOREIGN KEY (class_id) REFERENCES class (id)
);

DROP TABLE IF EXISTS lab_schedule_teacher;

# 多个老师上同一门课
CREATE TABLE lab_schedule_teacher
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    schedule_id  INT UNSIGNED NOT NULL comment '排课id',
    teacher_id   INT UNSIGNED NOT NULL comment '老师id',
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY (schedule_id, teacher_id),
    FOREIGN KEY (schedule_id) REFERENCES lab_schedule (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher (id)
);

DROP TABLE IF EXISTS major;

create table major
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    major_code   char(4)      not null comment '专业代号，如计算机（0405）',
    major_name   varchar(20)  not null comment '专业名',
    school_id    int unsigned not null comment '所属学院',
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    unique (major_name, school_id),
    foreign key (school_id) references school (id)
);

DROP TABLE IF EXISTS class;

create table class
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    class_name   varchar(3)   not null comment '班级名，如3（班）',
    major_id     int unsigned not null comment '专业id',
    year         int unsigned NOT NULL comment '入学年份',
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    unique (class_name, major_id, year),
    foreign key (major_id) references major (id)
);

set foreign_key_checks = true;
