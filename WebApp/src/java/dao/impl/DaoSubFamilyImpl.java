package dao.impl;


import dao.DaoSubFamily;
import db.ConnectDB;
import dto.SubFamily;
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
public class DaoSubFamilyImpl implements DaoSubFamily {

    private final ConnectDB con;
    private final StringBuilder sql;

    public DaoSubFamilyImpl() {
        this.con = new ConnectDB();
        this.sql = new StringBuilder();
    }

    @Override
    public List<SubFamily> getSubFamilies(int id) {
        sql.delete(0, sql.length())
                .append("SELECT sf.in_id AS in_id, sf.vc_name AS vc_name, sf.vc_description AS vc_description"
                        + " FROM t_subfamily sf"
                        + " INNER JOIN t_family_detail fd"
                        + " ON fd.in_id_subfamily = sf.in_id"
                        + " WHERE fd.in_id_familY = ? ");

        try (Connection cn = con.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                List<SubFamily> SubFamilies = new LinkedList<>();
                while (rs.next()) {
                    SubFamily subFamily = new SubFamily();
                    subFamily.setId(rs.getInt("in_id"));
                    subFamily.setName(rs.getString("vc_name"));
                    subFamily.setDescription(rs.getString("vc_description"));
                    SubFamilies.add(subFamily);
                }
                return SubFamilies;
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

}
