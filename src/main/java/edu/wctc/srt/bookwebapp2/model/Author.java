/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.srt.bookwebapp2.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Professional
 */
public class Author {
    
    private Integer id;
    private String name;
    private Date dateAdded;

   
    public Author() {
    }

    public Author(Integer id, String name, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
    }

    // validate setters and call them in constructor
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", name=" + name + ", dateAdded=" + dateAdded + '}';
    }
    
    
    
    
}
