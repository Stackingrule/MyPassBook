package com.stackingrule.passbook.mapper;

import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import com.stackingrule.passbook.constant.Constans;
import com.stackingrule.passbook.vo.User;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * <h1>HBase User Row to User Object</h1>
 */
public class UserRowMapper implements RowMapper<User> {

    private static byte[] FAMILY_B = Constans.UserTable.FAMILY_B.getBytes();
    private static byte[] NAME = Constans.UserTable.NAME.getBytes();
    private static byte[] AGE = Constans.UserTable.AGE.getBytes();
    private static byte[] SEX = Constans.UserTable.SEX.getBytes();

    private static byte[] FAMILY_O = Constans.UserTable.FAMILY_O.getBytes();
    private static byte[] PHONE = Constans.UserTable.PHONE.getBytes();
    private static byte[] ADDRESS = Constans.UserTable.ADDRESS.getBytes();


    @Override
    public User mapRow(Result result, int rowNum) throws Exception {

        User.BaseInfo baseInfo = new User.BaseInfo(
                Bytes.toString(result.getValue(FAMILY_B, NAME)),
                Bytes.toInt(result.getValue(FAMILY_B, AGE)),
                Bytes.toString(result.getValue(FAMILY_B, SEX))
        );

        User.OtherInfo otherInfo = new User.OtherInfo(
                Bytes.toString(result.getValue(FAMILY_O, PHONE)),
                Bytes.toString(result.getValue(FAMILY_O, ADDRESS))
        );

        return new User(
                Bytes.toLong(result.getRow()), baseInfo, otherInfo
        );
    }
}
