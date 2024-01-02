# lab_scheduler 开头由客户端程序修改，其他表仅admin能修改

set foreign_key_checks = false;

DROP TABLE IF EXISTS admin;

create table admin
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account      char(5)  not null,
    password     char(32) not null,
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    UNIQUE (account)
);

DROP TABLE IF EXISTS calendar;

create table calendar
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    year         INT UNSIGNED           NOT NULL COMMENT '年份',
    semester     CHAR(1)                NOT NULL COMMENT '学期（1、2）',
    start_date   date                   not null comment '如2023-08-21',
    active       bool     default false not null,
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    unique (start_date),
    unique (year, semester)
);

DROP TABLE IF EXISTS course;

create table course
(
    id           int unsigned auto_increment primary key,
    course_code  char(5)     not null comment '课程代号',
    course_name  varchar(40) not null,
    course_hour  varchar(3)  not null comment '学时',
#     school_id    int unsigned not null comment '课程所属学院，不同学院可能有相同课程',
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
#     unique (course_code, major_id, school_id),
#     foreign key
#     foreign key (school_id) references school (id)
);

DROP TABLE IF EXISTS course_major;

# 多对多 一门课不同专业学 一个专业学多门课
# 一门课对应多个老师，多个老师对应多个学院，已经有teacher_course，不必建立course_school
create table course_major
(
    id           int unsigned auto_increment primary key,
    course_id    int unsigned not null comment '课程id',
    major_id     int unsigned not null comment '专业id',
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    unique (course_id, major_id),
    foreign key (course_id) references course (id),
    foreign key (major_id) references major (id)
);

DROP TABLE IF EXISTS teacher;

# 不需要知道老师属于哪个专业，而是需要知道老师教哪些专业（通过teacher_course,course_major表）
create table teacher
(
    id           int unsigned auto_increment primary key,
    teacher_code char(5)      not null comment '教师工号',
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
# 一个老师教某个学院某个专业某个年级的某个班
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

DROP TABLE IF EXISTS teacher_class;

# 由老师教的classId确定专业年级班级，再由专业Id确认学院 major(schoolId)
create table teacher_class
(
    id           int unsigned auto_increment primary key,
    teacher_id   int unsigned not null,
    class_id     int unsigned not null,
    gmt_create   datetime default CURRENT_TIMESTAMP,
    gmt_modified datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    foreign key (teacher_id) REFERENCES teacher (id),
    foreign key (class_id) REFERENCES class (id)
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
    calendar_id  int unsigned            not null comment '校历id，如23年第1学期',
    week         CHAR(2)                 NOT NULL COMMENT '周次',
    weekday      CHAR(1)                 NOT NULL COMMENT '星期几',
    session      CHAR(1)                 NOT NULL COMMENT '第几次课，如1（12节）',
    lab_id       int unsigned            NOT NULL COMMENT '实验室id，确定学院和实验室名',
    course_id    int unsigned            not null comment '上课课程id，确定唯一课程名和上课的老师（可能多个）',
    major_id     int unsigned            not null comment '上课专业id，如计算机id5',
#     school_id    int unsigned         NOT NULL COMMENT '学院id',
#     grade        int unsigned         not null comment '上课年级，如2021级',
#     class        int unsigned         not null comment '上课班级，如1、2班',
#     teacher_id   CHAR(5)              NOT NULL comment '上课的老师 可能有多个',
    `lock`       CHAR(1)     DEFAULT '1' NOT NULL COMMENT '0表示管理员加的课, 1表示可排课, 2表示已排课，老师可能取消排课，添加此字段而避免删除记录的开销',
    note         VARCHAR(50) DEFAULT NULL comment '备注',
    gmt_create   DATETIME    DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    unique (calendar_id, week, weekday, session, lab_id),
    foreign key (calendar_id) references calendar (id),
    foreign key (lab_id) references lab (id),
    # 这里如果关联的是teacher_course表，则意味着只有课程有老师上课才能排课
    # 而关联course表，意味着即使该门课程没有老师上，也可以排课，但是没有老师也就不会开这门课了
    foreign key (course_id) references course (id)
);

# DROP TABLE IF EXISTS schedule_lab_course;

# 一个排课时间对应多个实验室，一个实验室对应一门课
# CREATE TABLE schedule_lab_course
# (
#     id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
#     schedule_id  INT UNSIGNED NOT NULL comment '排课id',
#     lab_id       INT UNSIGNED NOT NULL comment '实验室id',
#     course_id    INT UNSIGNED NOT NULL comment '课程id',
#     `lock`       CHAR(1)        DEFAULT '1' NOT NULL COMMENT '0表示管理员加的课, 1表示可排课, 2表示已排课，老师可能取消排课，添加此字段而避免删除记录的开销',
#     note         VARCHAR(50)    DEFAULT NULL comment '备注',
#     gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
#     gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
#     UNIQUE (schedule_id, lab_id),
#     FOREIGN KEY (schedule_id) REFERENCES lab_schedule (id),
#     FOREIGN KEY (lab_id) REFERENCES lab (id),
#     FOREIGN KEY (course_id) REFERENCES course(id)
# );

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
    school_id    int unsigned not null comment '所属学院，不同学院可能有同一门专业',
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    unique (major_code, school_id),
    foreign key (school_id) references school (id)
);

DROP TABLE IF EXISTS class;

# 确定xx专业xx年级xx班
create table class
(
    id           INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    class_name   varchar(2)   not null comment '班级名，如3（班）',
    major_id     int unsigned not null comment '专业id',
    year         int unsigned NOT NULL comment '入学年份',
    gmt_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    gmt_modified DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    unique (class_name, major_id, year),
    foreign key (major_id) references major (id)
);

set foreign_key_checks = true;
