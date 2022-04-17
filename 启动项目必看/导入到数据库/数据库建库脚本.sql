create table if not exists mk_user
(
    id          bigint             not null
    constraint mk_user_pk
    primary key,
    nickname    varchar(64)        not null,
    password    varchar(64)        not null,
    email       varchar(64)        not null,
    create_time timestamp          not null,
    age         integer,
    describe    varchar(1024),
    username    varchar(64)        not null,
    deleted     integer  default 0 not null,
    pic_url     text,
    update_time timestamp,
    status      smallint default 1 not null
    );

comment on table mk_user is '用户表';

comment on column mk_user.id is '主键ID';

comment on column mk_user.nickname is '昵称';

comment on column mk_user.password is '密码';

comment on column mk_user.email is '邮箱';

comment on column mk_user.create_time is '创建时间';

comment on column mk_user.age is '年龄';

comment on column mk_user.describe is '描述';

comment on column mk_user.username is '用户账号';

comment on column mk_user.status is '1：正常
-1：封禁
0：冻结';

alter table mk_user
    owner to postgres;

create unique index if not exists mk_user_id_uindex
    on mk_user (id);

create unique index if not exists mk_user_username_uindex
    on mk_user (username);

create unique index if not exists mk_user_email_uindex
    on mk_user (email);

create unique index if not exists mk_user_nickname
    on mk_user (nickname);

create table if not exists mk_user_auth
(
    id          bigserial
    constraint mk_user_auth_pk
    primary key,
    user_id     bigint not null,
    auth_id     bigint not null,
    create_time timestamp,
    deleted     integer default 0
);

comment on table mk_user_auth is '用户权限中间表';

comment on column mk_user_auth.id is '主键ID';

comment on column mk_user_auth.user_id is '用户ID';

comment on column mk_user_auth.auth_id is '权限ID';

alter table mk_user_auth
    owner to postgres;

create unique index if not exists mk_user_auth_id_uindex
    on mk_user_auth (id);

create table if not exists mk_auth
(
    id       bigint not null
    constraint mk_auth_pk
    primary key,
    descript varchar(64),
    role     smallint
    );

comment on table mk_auth is '权限';

comment on column mk_auth.id is '主键ID';

comment on column mk_auth.descript is '描述';

alter table mk_auth
    owner to postgres;

create unique index if not exists mk_auth_id_uindex
    on mk_auth (id);

create table if not exists mk_notes
(
    id           bigint                        not null
    constraint mk_notes_pk
    primary key,
    title        varchar(1024),
    content      text,
    deleted      integer default 0             not null,
    user_id      bigint                        not null,
    share_status integer default '-1'::integer not null,
    create_time  timestamp,
    update_time  timestamp,
    classic      integer default 1             not null
    );

comment on table mk_notes is '笔记表';

comment on column mk_notes.id is '表ID';

comment on column mk_notes.title is '标题';

comment on column mk_notes.content is '内容';

comment on column mk_notes.deleted is '逻辑删除';

comment on column mk_notes.user_id is '用户ID';

comment on column mk_notes.share_status is '-1 未分享，0待审核，1已分享,2审核未通过';

comment on column mk_notes.classic is '1 笔记 ';

alter table mk_notes
    owner to postgres;

create unique index if not exists mk_notes_id_uindex
    on mk_notes (id);

create index if not exists mk_notes_
    on mk_notes (share_status);

create table if not exists mk_logs
(
    id          bigint            not null
    constraint mk_logs_pk
    primary key,
    user_id     bigint            not null,
    action_msg  varchar(128),
    level       integer default 0 not null,
    action_mark integer           not null,
    create_time timestamp         not null
    );

comment on table mk_logs is '日志表';

comment on column mk_logs.id is '表ID';

comment on column mk_logs.user_id is '操作的用户ID';

comment on column mk_logs.action_msg is '操作信息';

comment on column mk_logs.level is '操作影响程度';

comment on column mk_logs.action_mark is '1.登录
2.文章操作
3.其他';

alter table mk_logs
    owner to postgres;

create unique index if not exists mk_logs_id_uindex
    on mk_logs (id);

create table if not exists mk_collect
(
    id          bigint       not null
    constraint mk_collect_pk
    primary key,
    user_id     bigint       not null,
    note_id     bigint       not null,
    create_time timestamp(6) not null,
    deleted     smallint default 0
    );

comment on table mk_collect is '收藏表';

comment on column mk_collect.id is '表ID';

comment on column mk_collect.user_id is '用户ID';

comment on column mk_collect.note_id is '笔记表ID';

comment on column mk_collect.create_time is '创建时间';

alter table mk_collect
    owner to postgres;

create unique index if not exists mk_collect_id_uindex
    on mk_collect (id);

create table if not exists mk_url_auth
(
    id          serial
    constraint mk_audit_pk
    primary key,
    url         varchar(256)      not null,
    auth_id     bigint            not null,
    state       integer           not null,
    create_time timestamp,
    deleted     integer default 0 not null
    );

comment on table mk_url_auth is '审核表';

comment on column mk_url_auth.url is '接口路径';

comment on column mk_url_auth.auth_id is '文章ID';

comment on column mk_url_auth.state is '审核状态(审核中:0)(审核通过:1)(审核不通过:-1)';

comment on column mk_url_auth.create_time is '创建时间';

comment on column mk_url_auth.deleted is '逻辑删除';

alter table mk_url_auth
    owner to postgres;

create unique index if not exists mk_audit_id_uindex
    on mk_url_auth (id);

create table if not exists mk_type
(
    id          bigint      not null
    constraint mk_type_pk
    primary key,
    name        varchar(64) not null,
    create_time timestamp   not null,
    user_id     bigint
    );

comment on table mk_type is '类型';

comment on column mk_type.id is '表ID';

comment on column mk_type.create_time is '创建时间';

alter table mk_type
    owner to postgres;

create index if not exists mk_type_name_index
    on mk_type (name);

create table if not exists mk_statistic
(
    id          bigint not null
    constraint mk_statistic_pk
    primary key,
    user_numb   integer,
    note_numb   integer,
    login_numb  integer,
    share_numb  integer,
    create_time date   not null
);

comment on column mk_statistic.id is '表id';

comment on column mk_statistic.user_numb is '用户总数';

comment on column mk_statistic.note_numb is '文章总数';

comment on column mk_statistic.login_numb is '登录次数';

comment on column mk_statistic.share_numb is '分享总数';

comment on column mk_statistic.create_time is '创建时间';

alter table mk_statistic
    owner to postgres;

create unique index if not exists mk_statistic_id_uindex
    on mk_statistic (id);

create table if not exists mk_type_note
(
    id      bigint not null
    constraint mk_type_note_pk
    primary key,
    user_id bigint not null,
    note_id bigint not null,
    type_id bigint not null
);

comment on table mk_type_note is '文章种类中间表';

comment on column mk_type_note.user_id is '用户ID';

comment on column mk_type_note.note_id is '文章ID';

comment on column mk_type_note.type_id is '类型ID';

alter table mk_type_note
    owner to postgres;

create unique index if not exists mk_type_note_id_uindex
    on mk_type_note (id);

create table if not exists mk_picture
(
    id          bigint       not null
    constraint mk_picture_pk
    primary key,
    url         varchar(256) not null,
    note_id     bigint,
    create_time timestamp,
    size        integer
    );

comment on column mk_picture.id is '表ID';

comment on column mk_picture.url is '图片地址';

comment on column mk_picture.note_id is '文章ID';

comment on column mk_picture.size is '图片大小';

alter table mk_picture
    owner to postgres;

create unique index if not exists mk_picture_id_uindex
    on mk_picture (id);

create table if not exists mk_scheduling
(
    id            serial,
    task_number   integer,
    create_time   timestamp,
    over_time     timestamp,
    update_number integer,
    start_time    timestamp
);

comment on column mk_scheduling.task_number is '1:代表 同步文章的定时任务
2:代表 文章新增定时任务
3:代表 用户新增定时任务';

comment on column mk_scheduling.over_time is '完成时间';

comment on column mk_scheduling.update_number is '更新的数量';

comment on column mk_scheduling.start_time is '开始时间';

alter table mk_scheduling
    owner to postgres;

create table if not exists xxl_job_group
(
    id           integer default nextval('xxl_job_group_id_seq'::regclass) not null
    primary key,
    app_name     varchar(64)                                               not null,
    title        varchar(12)                                               not null,
    address_type smallint                                                  not null,
    address_list text,
    update_time  timestamp(6)
    );

comment on column xxl_job_group.app_name is '执行器AppName';

comment on column xxl_job_group.title is '执行器名称';

comment on column xxl_job_group.address_type is '执行器地址类型：0=自动注册、1=手动录入';

comment on column xxl_job_group.address_list is '执行器地址列表，多地址逗号分隔';

alter table xxl_job_group
    owner to postgres;

create table if not exists xxl_job_info
(
    id                        integer default nextval('xxl_job_info_id_seq'::regclass) not null
    primary key,
    job_group                 integer                                                  not null,
    job_desc                  varchar(255)                                             not null,
    add_time                  timestamp(6),
    update_time               timestamp(6),
    author                    varchar(64),
    alarm_email               varchar(255),
    schedule_type             varchar(50)                                              not null,
    schedule_conf             varchar(128),
    misfire_strategy          varchar(50)                                              not null,
    executor_route_strategy   varchar(50),
    executor_handler          varchar(255),
    executor_param            varchar(512),
    executor_block_strategy   varchar(50),
    executor_timeout          integer                                                  not null,
    executor_fail_retry_count integer                                                  not null,
    glue_type                 varchar(50)                                              not null,
    glue_source               text,
    glue_remark               varchar(128),
    glue_updatetime           timestamp(6),
    child_jobid               varchar(255),
    trigger_status            smallint                                                 not null,
    trigger_last_time         bigint                                                   not null,
    trigger_next_time         bigint                                                   not null
    );

comment on column xxl_job_info.job_group is '执行器主键ID';

comment on column xxl_job_info.author is '作者';

comment on column xxl_job_info.alarm_email is '报警邮件';

comment on column xxl_job_info.schedule_type is '调度类型';

comment on column xxl_job_info.schedule_conf is '调度配置，值含义取决于调度类型';

comment on column xxl_job_info.misfire_strategy is '调度过期策略';

comment on column xxl_job_info.executor_route_strategy is '执行器路由策略';

comment on column xxl_job_info.executor_handler is '执行器任务handler';

comment on column xxl_job_info.executor_param is '执行器任务参数';

comment on column xxl_job_info.executor_block_strategy is '阻塞处理策略';

comment on column xxl_job_info.executor_timeout is '任务执行超时时间，单位秒';

comment on column xxl_job_info.executor_fail_retry_count is '失败重试次数';

comment on column xxl_job_info.glue_type is 'GLUE类型';

comment on column xxl_job_info.glue_source is 'GLUE源代码';

comment on column xxl_job_info.glue_remark is 'GLUE备注';

comment on column xxl_job_info.glue_updatetime is 'GLUE更新时间';

comment on column xxl_job_info.child_jobid is '子任务ID，多个逗号分隔';

comment on column xxl_job_info.trigger_status is '调度状态：0-停止，1-运行';

comment on column xxl_job_info.trigger_last_time is '上次调度时间';

comment on column xxl_job_info.trigger_next_time is '下次调度时间';

alter table xxl_job_info
    owner to postgres;

create table if not exists xxl_job_lock
(
    lock_name varchar(50) not null
    primary key
    );

comment on column xxl_job_lock.lock_name is '锁名称';

alter table xxl_job_lock
    owner to postgres;

create table if not exists xxl_job_log
(
    id                        integer  default nextval('xxl_job_log_id_seq'::regclass) not null
    primary key,
    job_group                 integer                                                  not null,
    job_id                    integer                                                  not null,
    executor_address          varchar(255),
    executor_handler          varchar(255),
    executor_param            varchar(512),
    executor_sharding_param   varchar(20),
    executor_fail_retry_count integer  default 0                                       not null,
    trigger_time              timestamp(6),
    trigger_code              integer                                                  not null,
    trigger_msg               text,
    handle_time               timestamp(6),
    handle_code               integer                                                  not null,
    handle_msg                text,
    alarm_status              smallint default 0                                       not null
    );

comment on column xxl_job_log.job_group is '执行器主键ID';

comment on column xxl_job_log.job_id is '任务，主键ID';

comment on column xxl_job_log.executor_address is '执行器地址，本次执行的地址';

comment on column xxl_job_log.executor_handler is '执行器任务handler';

comment on column xxl_job_log.executor_param is '执行器任务参数';

comment on column xxl_job_log.executor_sharding_param is '执行器任务分片参数，格式如 1/2';

comment on column xxl_job_log.executor_fail_retry_count is '失败重试次数';

comment on column xxl_job_log.trigger_time is '调度-时间';

comment on column xxl_job_log.trigger_code is '调度-结果';

comment on column xxl_job_log.trigger_msg is '调度-日志';

comment on column xxl_job_log.handle_time is '执行-时间';

comment on column xxl_job_log.handle_code is '执行-状态';

comment on column xxl_job_log.handle_msg is '执行-日志';

comment on column xxl_job_log.alarm_status is '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败';

alter table xxl_job_log
    owner to postgres;

create index if not exists "I_handle_code"
    on xxl_job_log (handle_code);

create index if not exists "I_trigger_time"
    on xxl_job_log (trigger_time);

create table if not exists xxl_job_log_report
(
    id            integer default nextval('xxl_job_log_report_id_seq'::regclass) not null
    primary key,
    trigger_day   timestamp(6),
    running_count integer                                                        not null,
    suc_count     integer                                                        not null,
    fail_count    integer                                                        not null,
    update_time   timestamp(6)
    );

comment on column xxl_job_log_report.trigger_day is '调度-时间';

comment on column xxl_job_log_report.running_count is '运行中-日志数量';

comment on column xxl_job_log_report.suc_count is '执行成功-日志数量';

comment on column xxl_job_log_report.fail_count is '执行失败-日志数量';

alter table xxl_job_log_report
    owner to postgres;

create index if not exists i_trigger_day
    on xxl_job_log_report (trigger_day);

create table if not exists xxl_job_logglue
(
    id          integer default nextval('xxl_job_logglue_id_seq'::regclass) not null
    primary key,
    job_id      integer                                                     not null,
    glue_type   varchar(50),
    glue_source text,
    glue_remark varchar(128)                                                not null,
    add_time    timestamp(6),
    update_time timestamp(6)
    );

comment on column xxl_job_logglue.job_id is '任务，主键ID';

comment on column xxl_job_logglue.glue_type is 'GLUE类型';

comment on column xxl_job_logglue.glue_source is 'GLUE源代码';

comment on column xxl_job_logglue.glue_remark is 'GLUE备注';

alter table xxl_job_logglue
    owner to postgres;

create table if not exists xxl_job_registry
(
    id             integer default nextval('xxl_job_registry_id_seq'::regclass) not null
    primary key,
    registry_group varchar(50)                                                  not null,
    registry_key   varchar(255)                                                 not null,
    registry_value varchar(255)                                                 not null,
    update_time    timestamp(6)
    );

alter table xxl_job_registry
    owner to postgres;

create index if not exists i_g_k_v
    on xxl_job_registry (registry_group, registry_key, registry_value);

create table if not exists xxl_job_user
(
    id         integer default nextval('xxl_job_user_id_seq'::regclass) not null
    primary key,
    username   varchar(50)                                              not null,
    password   varchar(50)                                              not null,
    role       smallint                                                 not null,
    permission varchar(255)
    );

comment on column xxl_job_user.username is '账号';

comment on column xxl_job_user.password is '密码';

comment on column xxl_job_user.role is '角色：0-普通用户、1-管理员';

comment on column xxl_job_user.permission is '权限：执行器ID列表，多个逗号分割';

alter table xxl_job_user
    owner to postgres;

create index if not exists i_username
    on xxl_job_user (username);

create table if not exists global_table
(
    xid                       varchar(128) not null
    primary key,
    transaction_id            bigint
    constraint global_table_pk_2
    unique,
    status                    smallint     not null,
    application_id            varchar(32),
    transaction_service_group varchar(32),
    transaction_name          varchar(128),
    timeout                   integer,
    begin_time                bigint,
    application_data          varchar(2000),
    gmt_create                timestamp,
    gmt_modified              timestamp,
    constraint global_table_pk
    unique (gmt_modified, status)
    );

alter table global_table
    owner to postgres;

create index if not exists global_table_gmt_modified_status_index
    on global_table (gmt_modified, status);

create index if not exists global_table_transaction_id_index
    on global_table (transaction_id);

create table if not exists branch_table
(
    branch_id         bigint       not null
    primary key,
    xid               varchar(128) not null
    constraint branch_table_pk
    unique,
    transaction_id    bigint,
    resource_group_id varchar(32),
    resource_id       varchar(256),
    branch_type       varchar(8),
    status            smallint,
    client_id         varchar(64),
    application_data  varchar(2000),
    gmt_create        timestamp(6),
    gmt_modified      timestamp(6)
    );

alter table branch_table
    owner to postgres;

create table if not exists lock_table
(
    row_key        varchar(128) not null
    primary key,
    xid            varchar(96),
    transaction_id bigint,
    branch_id      bigint       not null
    constraint lock_table_pk
    unique,
    resource_id    varchar(256),
    table_name     varchar(32),
    pk             varchar(36),
    gmt_create     timestamp,
    gmt_modified   timestamp
    );

alter table lock_table
    owner to postgres;

create table if not exists undo_log
(
    id            bigserial
    primary key,
    branch_id     bigint       not null,
    xid           varchar(100) not null,
    context       varchar(128) not null,
    rollback_info bytea        not null,
    log_status    integer      not null,
    log_created   timestamp    not null,
    log_modified  timestamp    not null,
    ext           varchar(100) default NULL::character varying
    );

alter table undo_log
    owner to postgres;

create index if not exists undo_log_xid_branch_id_index
    on undo_log (xid, branch_id);

