/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "system_example")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SystemExample.findAll", query = "SELECT s FROM SystemExample s")
    , @NamedQuery(name = "SystemExample.findById", query = "SELECT s FROM SystemExample s WHERE s.id = :id")
    , @NamedQuery(name = "SystemExample.findByLastaudit", query = "SELECT s FROM SystemExample s WHERE s.lastaudit = :lastaudit")
    , @NamedQuery(name = "SystemExample.findByName", query = "SELECT s FROM SystemExample s WHERE s.name = :name")})
public class SystemExample implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "lastaudit")
    private String lastaudit;
    @Column(name = "name")
    private String name;

    public SystemExample() {
    }

    public SystemExample(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastaudit() {
        return lastaudit;
    }

    public void setLastaudit(String lastaudit) {
        this.lastaudit = lastaudit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemExample)) {
            return false;
        }
        SystemExample other = (SystemExample) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.SystemExample[ id=" + id + " ]";
    }
    
}
