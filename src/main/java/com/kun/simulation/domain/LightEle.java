package com.kun.simulation.domain;

import java.math.BigDecimal;

/**
 * @author KUN
 * @date 2023/2/13
 **/

public class LightEle extends DatabaseDevice{
    public LightEle(int time, Long productId, String serialNumber, Long userId, BigDecimal firmwareVersion, Integer rssi) {
        super(time, productId, serialNumber, userId, firmwareVersion, rssi);
    }

    @Override
    public void initSummary() {

    }

    @Override
    public void initRemarkMap() {

    }
}
