CREATE TABLE `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(50),
    `last_name` VARCHAR(50),
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `last_modified_by` INT,
    `last_modified_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`last_modified_by`) REFERENCES `user`(`id`),
    FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `replix_office`.`user` (`id`, `username`, `email`, `password`, `first_name`, `last_name`, `last_modified_by`, `created_by`) VALUES ('1', 'YasithaB', 'yasitha.dev@gmail.com', '$2a$12$Co1qHqSYZwmYw11CrgA51u6l8Le8072XR/Ft1ZC7EdbUJl0PXVF8.', 'Yasitha', 'Bandara', '1', '1');
CREATE TABLE `role` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `role_name` VARCHAR(50) NOT NULL UNIQUE,
    `description` VARCHAR(255),
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `last_modified_by` INT,
    `last_modified_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`last_modified_by`) REFERENCES `user`(`id`),
    FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- AI promt:create mysql mapping table for above 2tables(user and role) as it has many to many relationship
CREATE TABLE `user_role` (
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `last_modified_by` INT,
    `last_modified_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `role_id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
    FOREIGN KEY (`last_modified_by`) REFERENCES `user`(`id`),
    FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `permission` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `permission_name` VARCHAR(50) NOT NULL UNIQUE,
    `description` VARCHAR(255),
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `last_modified_by` INT,
    `last_modified_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (`last_modified_by`) REFERENCES `user`(`id`),
    FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- AI promt:create mysql mapping table for above 2tables(role and permission) as it has many to many relationship
CREATE TABLE `role_permission` (
    `role_id` INT NOT NULL,
    `permission_id` INT NOT NULL,
    `active` BOOLEAN NOT NULL DEFAULT TRUE,
    `last_modified_by` INT,
    `last_modified_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_by` INT,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`role_id`, `permission_id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
    FOREIGN KEY (`permission_id`) REFERENCES `permission`(`id`),
    FOREIGN KEY (`last_modified_by`) REFERENCES `user`(`id`),
    FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;