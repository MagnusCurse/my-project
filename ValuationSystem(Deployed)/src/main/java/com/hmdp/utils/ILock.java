package com.hmdp.utils;

public interface ILock {
    /**
     * 用于获取锁, 并设置过期时间
     * @param timeOutSec
     * @return
     */
    boolean tryLock(long timeOutSec);

    void unLock();
}
