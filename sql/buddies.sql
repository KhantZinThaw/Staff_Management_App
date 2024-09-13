CREATE DATABASE `buddies`;

USE `buddies`;

CREATE TABLE `profile` (
    `profile_id` INT NOT NULL AUTO_INCREMENT ,
    `profile_name` VARCHAR(64) NOT NULL ,
    `profile_desc` VARCHAR(256) NOT NULL ,
    `profile_status` BOOLEAN NOT NULL,
    PRIMARY KEY (`profile_id`)
);

CREATE TABLE `role` (
    `role_id` INT NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(64) NOT NULL,
    `role_desc` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE `user_account` (
    `username` VARCHAR(64) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `f_name` VARCHAR(64) NOT NULL,
    `l_name` VARCHAR(64) NOT NULL,
    `email` VARCHAR(64) NOT NULL,
    `max_slot` INT NULL,
    `profile_id` INT NOT NULL,
    `role_id` INT NULL,
    `status` BOOLEAN NOT NULL,
    PRIMARY KEY (`username`),
    FOREIGN KEY (`profile_id`) REFERENCES profile(`profile_id`),
    FOREIGN KEY (`role_id`) REFERENCES role(`role_id`)
);

CREATE TABLE `work_slot` (
    `date` DATE NOT NULL,
    PRIMARY KEY (`date`)
);

CREATE TABLE `role_amount` (
    `date` DATE NOT NULL,
    `role_id` INT NOT NULL,
    `amount` INT NOT NULL,
    PRIMARY KEY (`date`, `role_id`),
    FOREIGN KEY (`date`) REFERENCES work_slot(`date`),
    FOREIGN KEY (`role_id`) REFERENCES role(`role_id`)
);

CREATE TABLE `bid` (
    `bid_id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(64) NOT NULL,
    `date` DATE NOT NULL,
	`bid_status` VARCHAR(64) NOT NULL,
    PRIMARY KEY (`bid_id`),
    FOREIGN KEY (`username`) REFERENCES user_account(`username`),
    FOREIGN KEY (`date`) REFERENCES work_slot(`date`)
);

-- Initial data
INSERT INTO `role` (role_name, role_desc) VALUES
    ('Waiter', 'Serving customer'),
    ('Cashier', 'Do transaction'),
    ('Chef', 'Cook food');

INSERT INTO `profile`(profile_name, profile_desc, profile_status) VALUES
    ('System Admin', 'Make user account', TRUE),
    ('Cafe Owner', 'Make work slot', TRUE),
    ('Cafe Manager', 'Manage bids', TRUE),
    ('Cafe Staff', 'Make bids', TRUE);
