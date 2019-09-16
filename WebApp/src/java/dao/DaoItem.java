/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Item;
import java.util.List;

/**
 *
 * @author carlr
 */
public interface DaoItem {
    List<Item> getItems(String id, String name);
    Item getItem(int id);
    boolean insertItem(Item item);
    boolean updateItem(Item item);
    boolean enableItem(int id, int status);
}
