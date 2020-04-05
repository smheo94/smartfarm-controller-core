package com.kt.smartfarm.lamplog.models;

public enum LOG_SECURITY_PERSONAL_INFO {
    id("아이디"),
    password("비밀번호"),
    phoneNumber("유선번호"),
    mobileNumber("무선번호"),
    email("이메일"),
    address("주소"),
    birthDate("생년월일"),
    sex("성별"),
    name("이름"),
    deviceId("기기식별자"),
    deviceModelName("기기모델명"),
    pricePlan("요금제"),
    residentNumber("주민등록번호"),
    passportNumber("여권번호"),
    driverLicenseNumber("운전면허번호"),
    creditCardNumber("신용카드번호"),
    bankAccountNumber("계좌번호"),
    locationInfo("위치정보"),
    subscriptionDate("(청약일) 그 외 필요 시 협의 후 추가");
    public String value;
    LOG_SECURITY_PERSONAL_INFO(String value) {
        this.value = value;
    }
    public String toString() {
        return this.name();
    }
}
