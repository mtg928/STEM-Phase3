/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "packages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Packages.findAll", query = "SELECT p FROM Packages p")
    , @NamedQuery(name = "Packages.findByPid", query = "SELECT p FROM Packages p WHERE p.pid = :pid")
    , @NamedQuery(name = "Packages.findByName", query = "SELECT p FROM Packages p WHERE p.name = :name")
    , @NamedQuery(name = "Packages.findByDescription", query = "SELECT p FROM Packages p WHERE p.description = :description")
    , @NamedQuery(name = "Packages.findByAuthorizedUser", query = "SELECT p FROM Packages p WHERE p.authorizedUser = :authorizedUser")
    , @NamedQuery(name = "Packages.findByConcurrentUser", query = "SELECT p FROM Packages p WHERE p.concurrentUser = :concurrentUser")
    , @NamedQuery(name = "Packages.findByFeeType", query = "SELECT p FROM Packages p WHERE p.feeType = :feeType")
    , @NamedQuery(name = "Packages.findByFee", query = "SELECT p FROM Packages p WHERE p.fee = :fee")
    , @NamedQuery(name = "Packages.findByAllowedCal", query = "SELECT p FROM Packages p WHERE p.allowedCal = :allowedCal")})
public class Packages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pid")
    private Integer pid;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "authorizedUser")
    private int authorizedUser;
    @Basic(optional = false)
    @Column(name = "concurrentUser")
    private int concurrentUser;
    @Basic(optional = false)
    @Column(name = "FeeType")
    private String feeType;
    @Basic(optional = false)
    @Column(name = "fee")
    private double fee;
    @Basic(optional = false)
    @Column(name = "allowedCal")
    private String allowedCal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "package1")
    private Collection<Users> usersCollection;

    public Packages() {
    }

    public Packages(Integer pid) {
        this.pid = pid;
    }

    public Packages(Integer pid, String name, String description, int authorizedUser, int concurrentUser, String feeType, double fee, String allowedCal) {
        this.pid = pid;
        this.name = name;
        this.description = description;
        this.authorizedUser = authorizedUser;
        this.concurrentUser = concurrentUser;
        this.feeType = feeType;
        this.fee = fee;
        this.allowedCal = allowedCal;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(int authorizedUser) {
        this.authorizedUser = authorizedUser;
    }

    public int getConcurrentUser() {
        return concurrentUser;
    }

    public void setConcurrentUser(int concurrentUser) {
        this.concurrentUser = concurrentUser;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getAllowedCal() {
        return allowedCal;
    }

    public void setAllowedCal(String allowedCal) {
        this.allowedCal = allowedCal;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pid != null ? pid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Packages)) {
            return false;
        }
        Packages other = (Packages) object;
        if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Packages[ pid=" + pid + " ]";
    }
    
}
