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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Convert;
import com.topfield.utilities.JSONObjectConverter;
import org.json.JSONObject;
/**
 *
 * @author Murali
 */
@Entity
@Table(name = "spghdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spghdr.findAll", query = "SELECT s FROM Spghdr s")
    , @NamedQuery(name = "Spghdr.findBySpghdrid", query = "SELECT s FROM Spghdr s WHERE s.spghdrid = :spghdrid")
    , @NamedQuery(name = "Spghdr.findByDescriptions", query = "SELECT s FROM Spghdr s WHERE s.descriptions = :descriptions")
    , @NamedQuery(name = "Spghdr.findByCalname", query = "SELECT s FROM Spghdr s WHERE s.calname = :calname")
    , @NamedQuery(name = "Spghdr.findByComments", query = "SELECT s FROM Spghdr s WHERE s.comments = :comments")
    , @NamedQuery(name = "Spghdr.findBySummary", query = "SELECT s FROM Spghdr s WHERE s.summary = :summary")
    , @NamedQuery(name = "Spghdr.findByCreateddate", query = "SELECT s FROM Spghdr s WHERE s.createddate = :createddate")})
public class Spghdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spghdrid")
    private Integer spghdrid;
    @Column(name = "descriptions")
    private String descriptions;
    @Basic(optional = false)
    @Column(name = "calname")
    private String calname;
    @Column(name = "comments")
    private String comments;
    @Column(name = "summary")
    private String summary;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    /*@Lob
    @Column(name = "prosettings")
    private String prosettings;*/
    @JoinColumn(name = "standard", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Standards standard;
    @JoinColumn(name = "spgid", referencedColumnName = "spg_id")
    @ManyToOne(optional = false)
    private SubProductGroup spgid;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users user;
    
        //@Lob
    //@Column(name = "prosettings")
    @Column(columnDefinition = "TEXT")
    @Convert(converter= JSONObjectConverter.class)
    private JSONObject prosettings;

    public Spghdr() {
    }

    public Spghdr(Integer spghdrid) {
        this.spghdrid = spghdrid;
    }

    public Spghdr(Integer spghdrid, String calname, Date createddate) {
        this.spghdrid = spghdrid;
        this.calname = calname;
        this.createddate = createddate;
    }

    public Integer getSpghdrid() {
        return spghdrid;
    }

    public void setSpghdrid(Integer spghdrid) {
        this.spghdrid = spghdrid;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getCalname() {
        return calname;
    }

    public void setCalname(String calname) {
        this.calname = calname;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public JSONObject getProsettings() {
        return prosettings;
    }

    public void setProsettings(JSONObject prosettings) {
        this.prosettings = prosettings;
    }

    public Standards getStandard() {
        return standard;
    }

    public void setStandard(Standards standard) {
        this.standard = standard;
    }

    public SubProductGroup getSpgid() {
        return spgid;
    }

    public void setSpgid(SubProductGroup spgid) {
        this.spgid = spgid;
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
        hash += (spghdrid != null ? spghdrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spghdr)) {
            return false;
        }
        Spghdr other = (Spghdr) object;
        if ((this.spghdrid == null && other.spghdrid != null) || (this.spghdrid != null && !this.spghdrid.equals(other.spghdrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Spghdr[ spghdrid=" + spghdrid + " ]";
    }
    
}
