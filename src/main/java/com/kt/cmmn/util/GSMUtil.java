package com.kt.cmmn.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Enumeration;

@Slf4j
public class GSMUtil {
    public static double round(double d, int n) {
        return Math.round(d * Math.pow(10, n)) / Math.pow(10, n);
    }

    public static String twoDigit(String value) {
        StringBuffer sb = new StringBuffer();
        if (Integer.parseInt(value) < 10) {
            sb.append("0");
            sb.append(value);
            return sb.toString();
        }
        return value;
    }

    public static Double getAdjustmentValue(Double sensorValue, Double sensorMinValaue, Double sensorMaxValue, Double adjustmentMinValue, Double adjustmentMaxValue) {
        Double locSensorValue = sensorValue;
        if (locSensorValue == null || sensorMinValaue == null || sensorMaxValue == null || adjustmentMinValue == null || adjustmentMaxValue == null || sensorMinValaue.equals(sensorMaxValue)) {
            return 0.0d;
        }
        if (adjustmentMaxValue - adjustmentMinValue == 0 || sensorMaxValue - sensorMinValaue == 0) {
            return 0.0d;
        }

        Double adjustment = (adjustmentMaxValue - adjustmentMinValue) / (sensorMaxValue - sensorMinValaue);
        if (locSensorValue > sensorMaxValue) {
            locSensorValue = sensorMaxValue;
        }
        if (locSensorValue < sensorMinValaue) {
            locSensorValue = sensorMinValaue;
        }
        return (locSensorValue - sensorMinValaue) * adjustment;
    }

    public static final int SETTING_TIME_CONDITION_BEFORE_SUNRISE = 1;
    public static final int SETTING_TIME_CONDITION_AFTER_SUNRISE = 2;
    public static final int SETTING_TIME_CONDITION_ABSOLUTE_TIME = 3;
    public static final int SETTING_TIME_CONDITION_BEFORE_SUNSET = 4;
    public static final int SETTING_TIME_CONDITION_AFTER_SUNSET = 5;
    public static final int SETTING_TIME_CONDITION_ALL_TIME = 6;

    public static Calendar getRealTime(Time relativeTime, int condition, boolean processingSec) {
        Calendar cal = getRealTime(relativeTime, condition);
        cal.set(Calendar.SECOND, 0);
        return cal;
    }
    public static Calendar getRealTime(Time relativeTime, int condition) {
        if (relativeTime == null || condition == SETTING_TIME_CONDITION_ALL_TIME) {
            return null;
        }
        TimeInfo timeInfo = SunriseSunset.SUNRIZESUNSET.getCurrentTimeInfo();
        //Calendar nowCal = SunrizeSunset.SUNRIZESUNSET.getCurrentTimeInfo().getNow();
        Calendar relativeStartCal = Calendar.getInstance();
        relativeStartCal.setTimeInMillis(relativeTime.getTime());
        Calendar realTime = Calendar.getInstance();
        switch (condition) {
            case SETTING_TIME_CONDITION_BEFORE_SUNRISE: // ������
                realTime.setTime(timeInfo.getSunrize().getTime());
                realTime.add(Calendar.HOUR_OF_DAY, -relativeStartCal.get(Calendar.HOUR_OF_DAY));
                realTime.add(Calendar.MINUTE, -relativeStartCal.get(Calendar.MINUTE));
                realTime.add(Calendar.SECOND, -relativeStartCal.get(Calendar.SECOND));
                break;
            case SETTING_TIME_CONDITION_AFTER_SUNRISE: // ������
                realTime.setTime(timeInfo.getSunrize().getTime());
                realTime.add(Calendar.HOUR_OF_DAY, relativeStartCal.get(Calendar.HOUR_OF_DAY));
                realTime.add(Calendar.MINUTE, relativeStartCal.get(Calendar.MINUTE));
                realTime.add(Calendar.SECOND, relativeStartCal.get(Calendar.SECOND));
                break;
            case SETTING_TIME_CONDITION_ABSOLUTE_TIME: // ����ð�
                realTime.set(Calendar.HOUR_OF_DAY, relativeStartCal.get(Calendar.HOUR_OF_DAY));
                realTime.set(Calendar.MINUTE, relativeStartCal.get(Calendar.MINUTE));
                realTime.set(Calendar.SECOND, relativeStartCal.get(Calendar.SECOND));
                break;
            case SETTING_TIME_CONDITION_BEFORE_SUNSET: // �ϸ���
                realTime.setTime(timeInfo.getSunset().getTime());
                realTime.add(Calendar.HOUR_OF_DAY, -relativeStartCal.get(Calendar.HOUR_OF_DAY));
                realTime.add(Calendar.MINUTE, -relativeStartCal.get(Calendar.MINUTE));
                realTime.add(Calendar.SECOND, -relativeStartCal.get(Calendar.SECOND));
                break;
            case SETTING_TIME_CONDITION_AFTER_SUNSET: // �ϸ���
                realTime.setTime(timeInfo.getSunset().getTime());
                realTime.add(Calendar.HOUR_OF_DAY, relativeStartCal.get(Calendar.HOUR_OF_DAY));
                realTime.add(Calendar.MINUTE, relativeStartCal.get(Calendar.MINUTE));
                realTime.add(Calendar.SECOND, relativeStartCal.get(Calendar.SECOND));
                break;
            default:
                realTime = null;
                break;
        }
        return realTime;
    }


    public static String getLocalIpAddress() {
        String result = getLocalIpAddress(false);
        if (result == null) {
            result = getLocalIpAddress(true);
        }
        return result;
    }

    public static String getLocalIpAddress(boolean isSiteLocal) {
        String result = null;
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();

                if (!intf.getName().startsWith("eth")) {
                    continue;
                }

                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress() == isSiteLocal) {
                        result = inetAddress.getHostAddress();
                        break;
                    }

                }
                if (result != null) {
                    break;
                }
            }
        } catch (SocketException ex) {
            log.error("getLocalIpAddress", ex);
        }
        return result;

    }

    public Double getPBand(Double outTemp, Double windSpeed, Double minPBand, Double maxPBand, Double minOutTemp,
                           Double maxOutTemp, Double minWindSpeed, Double maxWindSpeed) {

        Double wSpeed = windSpeed;
        if (wSpeed == null) {
            wSpeed = 0d;
        }
        if (minWindSpeed != null && wSpeed < minWindSpeed) {
            wSpeed = minWindSpeed;
        }
        if (maxWindSpeed != null && wSpeed > maxWindSpeed) {
            wSpeed = maxWindSpeed;
        }
        // 외부 최대 최소 기온차
        Double outTempRange = (maxOutTemp - minOutTemp);
        Double g = (minPBand - maxPBand) / outTempRange;
        Double c = ((maxPBand * (maxOutTemp + wSpeed)) - (minPBand * (minOutTemp + wSpeed))) / outTempRange;

        Double pBand = g * outTemp + c;
        if (pBand < minPBand) {
            pBand = minPBand;
        }
        if (pBand > maxPBand) {
            pBand = maxPBand;
        }
        return pBand;
    }


    public static boolean isResetTime(Long now, Long resetTime, Long lastResetTime) {
        if (resetTime == null || resetTime == 0) {
            return false;
        }
        //1분이내 리셋예정이고 , 마지막 리셋시간이 리셋시간보다 작으면
        boolean reset = resetTime + (60 * 1000) < now && lastResetTime + (60 * 1000) < resetTime;
        if (reset) {
            log.info("Reset Time : {}, {}, {}", now, resetTime, lastResetTime);
        }
        return reset;
    }

    public static String getGSMUrl(String systemHost, String systemPort) {
        if( systemHost.startsWith("https://")) {
            return systemHost + ":" + systemPort;
        } else {
            return "https://" + systemHost + ":" + systemPort;
        }
    }

}
