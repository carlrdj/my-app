/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author carlr
 */
public class Item {    
    private int id;
    private int id_family_detail;
    private String family;
    private int id_family;
    private String subfamily;
    private int id_subfamily;
    private String name;
    private String description;
    private int status;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_family_detail() {
        return id_family_detail;
    }

    public void setId_family_detail(int id_family_detail) {
        this.id_family_detail = id_family_detail;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getId_family() {
        return id_family;
    }

    public void setId_family(int id_family) {
        this.id_family = id_family;
    }

    public String getSubfamily() {
        return subfamily;
    }

    public void setSubfamily(String subfamily) {
        this.subfamily = subfamily;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_subfamily() {
        return id_subfamily;
    }

    public void setId_subfamily(int id_subfamily) {
        this.id_subfamily = id_subfamily;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
