DROP DATABASE IF EXISTS library_paper_project;
CREATE DATABASE library_paper_project;
USE library_paper_project;

DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `time_period`;
DROP TABLE IF EXISTS `journal_info`;
DROP TABLE IF EXISTS `paper_info`;
DROP TABLE IF EXISTS `user_info`;


CREATE TABLE `category`
(
  `category_id`   INT         NOT NULL AUTO_INCREMENT COMMENT '学科类别ID',
  `category_name` VARCHAR(88) NOT NULL COMMENT '学科类别名称',
  PRIMARY KEY (`category_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1024
  DEFAULT CHARSET = utf8 COMMENT ='学科类别表';


CREATE TABLE `time_period`
(
  `time_id` INT NOT NULL COMMENT '时间编号',
  `year`    INT NOT NULL COMMENT '年',
  `month`   INT NOT NULL COMMENT '月',
  PRIMARY KEY (`time_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='时间段表';

CREATE TABLE `journal_info`
(
  `journal_id`  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '期刊ID',
  `full_title`  varchar(560) NOT NULL COMMENT 'full_title属性',
  `title_29`    varchar(400) NOT NULL COMMENT 'title_29属性',
  `title_20`    varchar(400) NOT NULL COMMENT 'title_20属性',
  `issn`        varchar(48) DEFAULT NULL COMMENT 'issn属性',
  `eissn`       varchar(48) DEFAULT NULL COMMENT 'eissn属性',
  `category_id` int(11)      NOT NULL COMMENT '类别id',
  `time_id`     int(11)      NOT NULL COMMENT '时间id',
  PRIMARY KEY (`journal_id`),
  KEY `time_id_index` (`time_id`),
  KEY `category_id_index` (`category_id`),
  FULLTEXT KEY `full_title_index` (`full_title`),
  FOREIGN KEY (`time_id`) REFERENCES `time_period` (`time_id`),
  FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='期刊信息表';


CREATE TABLE `paper_info`
(
  `paper_id`         BIGINT       NOT NULL AUTO_INCREMENT,
  `accession_number` CHAR(20)     NOT NULL,
  `doi`              VARCHAR(64)  NOT NULL,
  `pmid`             VARCHAR(64)  NOT NULL,
  `article_name`     TEXT         NOT NULL,
  `authors`          TEXT         NOT NULL,
  `source`           VARCHAR(256) NOT NULL,
  `research_field`   VARCHAR(32)  NOT NULL,
  `times_cited`      INT          NOT NULL,
  `countries`        TEXT         NOT NULL,
  `addresses`        TEXT         NOT NULL,
  `institutions`     TEXT         NOT NULL,
  `publication_date` INT          NOT NULL,
  `category_id`      INT          NOT NULL,
  `time_id`          int(11)      NOT NULL,
  `paper_type`       INT          NOT NULL,
  PRIMARY KEY (`paper_id`),
  KEY `accession_number_index` (`accession_number`),
  KEY `publication_date_index` (`publication_date`),
  KEY `category_id_index` (`category_id`),
  KEY `paper_type_index` (`paper_type`),
  KEY `doi_index` (`doi`),
  FULLTEXT KEY `article_name_full_text_index` (`article_name`),
  FOREIGN KEY (`time_id`) REFERENCES `time_period` (`time_id`),
  FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
# `doi`, `article_name`

CREATE TABLE `user_info`
(
  `user_name`  varchar(64) NOT NULL COMMENT '用户名',
  `password`   varchar(64) NOT NULL COMMENT '密码',
  `role`       varchar(64) NOT NULL COMMENT '角色',
  `permission` varchar(64) NOT NULL COMMENT '权限',
  PRIMARY KEY (`user_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='用户信息表';

# 28+ 9 + 6

CREATE TABLE `base_line_info`
(
  `base_line_id` VARCHAR(64) NOT NULL COMMENT '基准线的id',
  `year`         VARCHAR(16) NOT NULL COMMENT '年',
  `value`        INT         NOT NULL COMMENT '值',
  `percent`      VARCHAR(8)  NOT NULL,
  `category_id`  INT         NOT NULL COMMENT '类别id',
  PRIMARY KEY (`base_line_id`),
  KEY `year_id_index` (`year`),
  KEY `category_id_index` (`category_id`),
  FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT = '基准线信息表';

INSERT INTO `category`
VALUES (1020, 'AGRICULTURAL SCIENCES'),
       (1023, 'ARTS & HUMANITIES'),
       (1001, 'BIOLOGY & BIOCHEMISTRY'),
       (1015, 'CHEMISTRY'),
       (1002, 'CLINICAL MEDICINE'),
       (1011, 'COMPUTER SCIENCE'),
       (1007, 'ECONOMICS & BUSINESS'),
       (1022, 'ENGINEERING'),
       (1004, 'ENVIRONMENT/ECOLOGY'),
       (1019, 'GEOSCIENCES'),
       (1009, 'IMMUNOLOGY'),
       (1008, 'MATERIALS SCIENCE'),
       (1010, 'MATHEMATICS'),
       (1006, 'MICROBIOLOGY'),
       (1014, 'MOLECULAR BIOLOGY & GENETICS'),
       (1021, 'Multidisciplinary'),
       (1017, 'NEUROSCIENCE & BEHAVIOR'),
       (1013, 'PHARMACOLOGY & TOXICOLOGY'),
       (1018, 'PHYSICS'),
       (1003, 'PLANT & ANIMAL SCIENCE'),
       (1016, 'PSYCHIATRY/PSYCHOLOGY'),
       (1005, 'SOCIAL SCIENCES, GENERAL'),
       (1012, 'SPACE SCIENCE');