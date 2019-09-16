package dao.impl;


import dao.DaoFamily;
import db.ConnectDB;
import dto.Family;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author carlr
 */
public class DaoFamilyImpl implements DaoFamily {

    private final ConnectDB con;
    private final StringBuilder sql;

    public DaoFamilyImpl() {
        this.con = new ConnectDB();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Family> getFamilies() {
        sql.delete(0, sql.length())
                .append("SELECT in_id, vc_name, vc_description FROM t_family");

        try (Connection cn = con.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());
                ResultSet rs = ps.executeQuery()) {

            List<Family> families = new LinkedList<>();
            while (rs.next()) {
                Family family = new Family();
                family.setId(rs.getInt("in_id"));
                family.setName(rs.getString("vc_name"));
                family.setDescription(rs.getString("vc_description"));
                families.add(family);
            }
            return families;
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

}
