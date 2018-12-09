package com.oraclewdp.ddbookmarket.dao.impl;

import com.oraclewdp.ddbookmarket.dao.AdminDao;
import com.oraclewdp.ddbookmarket.model.Admin;

import com.oraclewdp.ddbookmarket.util.DBUtil;
import com.oraclewdp.ddbookmarket.util.MD5Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class AdminDaoJdbcImpl implements AdminDao {
    @Override
    public boolean find(Admin admin) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
        /*   String sql = "select * from t_admin where name=? and pwd=?";
            stmt=conn.prepareStatement(sql);
            stmt.setString(1,admin.getName());
            //加密，避免信息斜路

            try {
                stmt.setString(2, MD5Util.getEncryptedPwd(admin.getPwd()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            rs = stmt.executeQuery();*/
        //盐在当前用户密码那一列，我们要对密码那一列进行加密，先拿到盐，再加密
           String sql = "select pwd from t_admin where name=? ";
            stmt=conn.prepareStatement(sql);
            stmt.setString(1,admin.getName());
            rs = stmt.executeQuery();
            if(rs.next()) {
                String dbPwd = rs.getString(1);
                try {
                    return  MD5Util.validPasswd(admin.getPwd(),dbPwd);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
 /*       if(rs.next()){
            return  true;
        }*/
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.free(rs, stmt, conn);
        }
        return false;
    }
}
