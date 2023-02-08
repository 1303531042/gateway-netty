package com.kun.simulation.domain;

/**
 * @author KUN
 * @date 2023/2/8
 **/
public abstract class DatabaseDevice extends Device {
    private int dateTime;
    @Override
    public void checkOffline() {
        if (dateTime > 3) {
            setOnline(false);//然后发布 info
        }

    }

    @Override
    public void checkOnline() {
        if (dateTime == 0) {
            setOnline(true);//然后发布 info 但是影子模式是通过 emqx钩子实现的 虽然设备有上下线 但是emxq一直在线 导致 设备影子值无法触发
        }

    }

    public void setDateTime(int dateTime) {
        this.dateTime = dateTime;
        if (isOnline()) {
            checkOffline();
        } else {
            checkOnline();
        }
    }
}
