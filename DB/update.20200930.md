# 풀무원강화공장
```sql
INSERT INTO `cd_controller_info` (`id`, `model`, `name`, `homepage`, `email`, `phone`, `last_update`, `name_i18n`) VALUES ('51', 'pulmu_bean', '콩나물제어기', NULL, NULL, NULL, '2020-01-17 22:16:24', '__common.controller_info.pulmu_bean');
```


# 앱검증 관련 추가
```sql
ALTER TABLE `app_history` ADD COLUMN `fcm_key` VARCHAR(4096) NULL COMMENT 'GoogleFCMName' AFTER `download_url`; 


INSERT INTO app_history (     id, app_name, app_type, app_version, update_date, download_url, fcm_key)
VALUES    (
                '3', 'com.kt.android.app.smartfarm', 'android_app', '1.1', NOW(), 'https://smartfarm.kt.co.kr/download/com_kt_smartfarm_v4.apk', 'AAAAJPlHnrY:APA91bE31bpYSqAI9diqFw3QAbbDvcPkfIo-Dv8BqPjxDM2pY905q4x4xNTAlrYOMWWBx-2HGBLcYh9oafdflGSlsb0HDrjjhYckewFuMv-cUjH7k-dj0EfiQKX9AlNep3g3LwiXvg70'
        );        
        
ALTER TABLE `phone_setting` 
ADD COLUMN `package_name` VARCHAR(4096) NULL COMMENT '설치된패키지이름' AFTER `privacy_policy_agree_date`; 

```

 
 
 