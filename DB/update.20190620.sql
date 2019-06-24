ALTER TABLE `house_diary_file` ADD COLUMN `file_idx` BIGINT NOT  NULL AUTO_INCREMENT AFTER `update_date`, ADD KEY(`file_idx`);
ALTER TABLE `crops_diary_file` ADD COLUMN `file_idx` BIGINT NOT NULL AUTO_INCREMENT AFTER `update_date`, ADD KEY(`file_idx`);
ALTER TABLE `crops_diary_file` DROP PRIMARY KEY, ADD PRIMARY KEY (`file_idx`);
ALTER TABLE `house_diary_file` DROP PRIMARY KEY, ADD PRIMARY KEY (`file_idx`);