package com.ksp.util;

import java.math.BigInteger;
import java.util.Map;

//OD信息获取
public interface ODPassenger {
    /**
     *连接数据库，返回路网上的所有OD对和经过该OD对的总人数
     */
    Map<OD, BigInteger> odPassengerCount();

}