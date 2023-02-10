package com.kun.simulation.domain;

import java.math.BigDecimal;

/**
 * @author KUN
 * @date 2023/2/8
 **/
public abstract class DatabaseDevice extends Device {

    /**
     * 未读到数据库上传次数
     */
    private int dateTime;

    /**
     * 未读到数据库上传次数允许最大值
     */
    private final int time;

    public DatabaseDevice(int time, Long productId, String serialNumber, Long userId, BigDecimal firmwareVersion, Integer rssi) {
        super(productId, serialNumber, userId, firmwareVersion, rssi);
        this.time = time;
    }

    @Override
    public boolean checkOffline() {
        if (dateTime > time) {
            setOnline(false);//然后发布 info
            return true;
        }
        return false;

    }

    @Override
    public boolean checkOnline() {
        if (dateTime == 0) {
            setOnline(true);
            return true;
        }
        return false;
    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
        checkStatus();
    }
}
