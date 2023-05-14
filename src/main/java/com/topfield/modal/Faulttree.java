/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "faulttree")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faulttree.findAll", query = "SELECT f FROM Faulttree f")
    , @NamedQuery(name = "Faulttree.findByFaultId", query = "SELECT f FROM Faulttree f WHERE f.faultId = :faultId")
    , @NamedQuery(name = "Faulttree.findByFaultName", query = "SELECT f FROM Faulttree f WHERE f.faultName = :faultName")
    , @NamedQuery(name = "Faulttree.findByFaultType", query = "SELECT f FROM Faulttree f WHERE f.faultType = :faultType")
    , @NamedQuery(name = "Faulttree.findByFaultResult", query = "SELECT f FROM Faulttree f WHERE f.faultResult = :faultResult")
    , @NamedQuery(name = "Faulttree.findByComments", query = "SELECT f FROM Faulttree f WHERE f.comments = :comments")
    , @NamedQuery(name = "Faulttree.findByCreateddate", query = "SELECT f FROM Faulttree f WHERE f.createddate = :createddate")})
public class Faulttree implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "faultId")
    private Integer faultId;
    @Column(name = "faultName")
    private String faultName;
    @Column(name = "faultType")
    private String faultType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "faultResult")
    private Double faultResult;
    @Column(name = "Comments")
    private String comments;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.DATE)
    private Date createddate;
    @JoinColumn(name = "faultComponent", referencedColumnName = "mpg_id")
    @ManyToOne(optional = false)
    private MainProductGroup faultComponent;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users user;

    public Faulttree() {
    }

    public Faulttree(Integer faultId) {
        this.faultId = faultId;
    }

    public Faulttree(Integer faultId, Date createddate) {
        this.faultId = faultId;
        this.createddate = createddate;
    }

    public Integer getFaultId() {
        return faultId;
    }

    public void setFaultId(Integer faultId) {
        this.faultId = faultId;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public Double getFaultResult() {
        return faultResult;
    }

    public void setFaultResult(Double faultResult) {
        this.faultResult = faultResult;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public MainProductGroup getFaultComponent() {
        return faultComponent;
    }

    public void setFaultComponent(MainProductGroup faultComponent) {
        this.faultComponent = faultComponent;
    }

    public TclProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(TclProjects projectId) {
        this.projectId = projectId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faultId != null ? faultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faulttree)) {
            return false;
        }
        Faulttree other = (Faulttree) object;
        if ((this.faultId == null && other.faultId != null) || (this.faultId != null && !this.faultId.equals(other.faultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Faulttree[ faultId=" + faultId + " ]";
    }
    
}
