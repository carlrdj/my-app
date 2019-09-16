/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import dao.DaoItem;
import db.ConnectDB;
import dto.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author carlr
 */
public class DaoItemImpl implements DaoItem {

    private final ConnectDB con;
    private final StringBuilder sql;

    public DaoItemImpl() {
        this.con = new ConnectDB();
        this.sql = new StringBuilder();
    }

    @Override
    public List<Item> getItems(String id, String name) {
        sql.delete(0, sql.length())
                .append("SELECT i.in_id AS in_id, fd.in_id AS in_id_family_detail, f.in_id AS in_id_family, f.vc_name AS vc_family, sf.in_id AS id_subfamily, sf.vc_name AS vc_subfamily, i.vc_name AS vc_name, i.vc_description AS vc_description, i.in_status AS in_status"
                        + " FROM t_item i"
                        + " INNER JOIN t_family_detail fd"
                        + " ON fd.in_id = i.in_id_family_detail"
                        + " INNER JOIN t_family f"
                        + " ON f.in_id = fd.in_id_family"
                        + " INNER JOIN t_subfamily sf"
                        + " ON sf.in_id = fd.in_id_subfamily "
                        + " ");

        if (id != "") {
            sql.append(" WHERE i.in_id = " + id);
        } else if (name != "") {
            sql.append(" WHERE i.vc_name LIKE '%" + name + "%' ");
        }

        try (Connection cn = con.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());
                ResultSet rs = ps.executeQuery();) {
            List<Item> items = new LinkedList<>();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("in_id"));
                item.setId_family_detail(rs.getInt("in_id_family_detail"));
                item.setId_family(rs.getInt("in_id_family"));
                item.setFamily(rs.getString("vc_family"));
                item.setId_subfamily(rs.getInt("id_subfamily"));
                item.setSubfamily(rs.getString("vc_subfamily"));
                item.setName(rs.getString("vc_name"));
                item.setDescription(rs.getString("vc_description"));
                item.setStatus(rs.getInt("in_status"));
                items.add(item);
            }
            return items;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Item getItem(int id) {
        sql.delete(0, sql.length())
                .append("SELECT i.in_id AS in_id, fd.in_id AS in_id_family_detail, f.in_id AS in_id_family, f.vc_name AS vc_family, sf.in_id AS id_subfamily, sf.vc_name AS vc_subfamily, i.vc_name AS vc_name, i.vc_description AS vc_description, i.in_status AS in_status"
                        + " FROM t_item i"
                        + " INNER JOIN t_family_detail fd"
                        + " ON fd.in_id = i.in_id_family_detail"
                        + " INNER JOIN t_family f"
                        + " ON f.in_id = fd.in_id_family"
                        + " INNER JOIN t_subfamily sf"
                        + " ON sf.in_id = fd.in_id_subfamily"
                        + " WHERE i.in_status != 3 AND i.in_id = ?");

        try (Connection cn = con.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt("in_id"));
                    item.setId_family_detail(rs.getInt("in_id_family_detail"));
                    item.setId_family(rs.getInt("in_id_family"));
                    item.setFamily(rs.getString("vc_family"));
                    item.setId_subfamily(rs.getInt("id_subfamily"));
                    item.setSubfamily(rs.getString("vc_subfamily"));
                    item.setName(rs.getString("vc_name"));
                    item.setDescription(rs.getString("vc_description"));
                    item.setStatus(rs.getInt("in_status"));
                    return item;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean insertItem(Item item) {
        sql.delete(0, sql.length())
                .append("INSERT INTO t_item (in_id_family_detail, vc_name, vc_description, in_status) VALUES "
                        + " ((SELECT in_id FROM t_family_detail WHERE in_id_family = ? AND in_id_subfamily = ?), ?, ? ,1)");

        try (Connection cn = con.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) {
            ps.setInt(1, item.getId_family());
            ps.setInt(2, item.getId_subfamily());
            ps.setString(3, item.getName());
            ps.setString(4, item.getDescription());
            int cto = ps.executeUpdate();
            if (cto == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateItem(Item item) {
        sql.delete(0, sql.length())
                .append("UPDATE t_item SET in_id_family_detail = (SELECT in_id FROM t_family_detail WHERE in_id_family = ? AND in_id_subfamily = ?), vc_name = ?, vc_description = ?"
                        + " WHERE in_id = ?");

        try (Connection cn = con.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) {
            ps.setInt(1, item.getId_family());
            ps.setInt(2, item.getId_subfamily());
            ps.setString(3, item.getName());
            ps.setString(4, item.getDescription());
            ps.setInt(5, item.getId());
            int cto = ps.executeUpdate();
            if (cto == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean enableItem(int id, int status) {
        sql.delete(0, sql.length())
                .append("UPDATE t_item SET in_status = ?"
                        + " WHERE in_id = ?");

        try (Connection cn = con.getConnection();
                PreparedStatement ps = cn.prepareCall(sql.toString());) {
            ps.setInt(1, status);
            ps.setInt(2, id);
            int cto = ps.executeUpdate();
            if (cto == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
