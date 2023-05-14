/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.topfield.modal;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.Convert;
import com.topfield.utilities.JSONObjectConverter;
import org.json.JSONObject;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "spfhdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spfhdr.findAll", query = "SELECT s FROM Spfhdr s")
    , @NamedQuery(name = "Spfhdr.findBySpfhdrid", query = "SELECT s FROM Spfhdr s WHERE s.spfhdrid = :spfhdrid")
    , @NamedQuery(name = "Spfhdr.findByDescriptions", query = "SELECT s FROM Spfhdr s WHERE s.descriptions = :descriptions")
    , @NamedQuery(name = "Spfhdr.findByCalname", query = "SELECT s FROM Spfhdr s WHERE s.calname = :calname")
    , @NamedQuery(name = "Spfhdr.findBySpfType", query = "SELECT s FROM Spfhdr s WHERE s.spfType = :spfType")
    , @NamedQuery(name = "Spfhdr.findByComments", query = "SELECT s FROM Spfhdr s WHERE s.comments = :comments")
    , @NamedQuery(name = "Spfhdr.findBySummary", query = "SELECT s FROM Spfhdr s WHERE s.summary = :summary")
    , @NamedQuery(name = "Spfhdr.findByCreateddate", query = "SELECT s FROM Spfhdr s WHERE s.createddate = :createddate")})
public class Spfhdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spfhdrid")
    private Integer spfhdrid;
    @Column(name = "descriptions")
    private String descriptions;
    @Basic(optional = false)
    @Column(name = "calname")
    private String calname;
    @Column(name = "spfType")
    private String spfType;
    @Column(name = "comments")
    private String comments;
    @Column(name = "summary")
    private String summary;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createddate;
    @Basic(optional = false)
    @Column(name = "approach")
    private int approach;
    /*@Lob
    @Column(name = "prosettings")
    private String prosettings;*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccfHdr")
    private Collection<Ccf> ccfCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vtxspfhdr")
    private Collection<Graphedge> graphedgeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "faultHeader")
    private Collection<Faultdata> faultdataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vtxspfhdr")
    private Collection<Graphvertex> graphvertexCollection;
    @JoinColumn(name = "spfid", referencedColumnName = "calcFunId")
    @ManyToOne(optional = false)
    private CalcFunctions spfid;
    @JoinColumn(name = "standard", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Standards standard;
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

    public Spfhdr() {
    }

    public Spfhdr(Integer spfhdrid) {
        this.spfhdrid = spfhdrid;
    }

    public Spfhdr(Integer spfhdrid, String calname, Date createddate) {
        this.spfhdrid = spfhdrid;
        this.calname = calname;
        this.createddate = createddate;
    }

    public Integer getSpfhdrid() {
        return spfhdrid;
    }

    public void setSpfhdrid(Integer spfhdrid) {
        this.spfhdrid = spfhdrid;
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

    public String getSpfType() {
        return spfType;
    }

    public void setSpfType(String spfType) {
        this.spfType = spfType;
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

    @XmlTransient
    public Collection<Ccf> getCcfCollection() {
        return ccfCollection;
    }

    public void setCcfCollection(Collection<Ccf> ccfCollection) {
        this.ccfCollection = ccfCollection;
    }

    @XmlTransient
    public Collection<Graphedge> getGraphedgeCollection() {
        return graphedgeCollection;
    }

    public void setGraphedgeCollection(Collection<Graphedge> graphedgeCollection) {
        this.graphedgeCollection = graphedgeCollection;
    }

    @XmlTransient
    public Collection<Faultdata> getFaultdataCollection() {
        return faultdataCollection;
    }

    public void setFaultdataCollection(Collection<Faultdata> faultdataCollection) {
        this.faultdataCollection = faultdataCollection;
    }

    @XmlTransient
    public Collection<Graphvertex> getGraphvertexCollection() {
        return graphvertexCollection;
    }

    public void setGraphvertexCollection(Collection<Graphvertex> graphvertexCollection) {
        this.graphvertexCollection = graphvertexCollection;
    }

    public CalcFunctions getSpfid() {
        return spfid;
    }

    public void setSpfid(CalcFunctions spfid) {
        this.spfid = spfid;
    }

    public Standards getStandard() {
        return standard;
    }
    public int getApproach() {
        return approach;
    }

    public void setApproach(int approach) {
        this.approach = approach;
    }

    public void setStandard(Standards standard) {
        this.standard = standard;
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
        hash += (spfhdrid != null ? spfhdrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spfhdr)) {
            return false;
        }
        Spfhdr other = (Spfhdr) object;
        if ((this.spfhdrid == null && other.spfhdrid != null) || (this.spfhdrid != null && !this.spfhdrid.equals(other.spfhdrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Spfhdr[ spfhdrid=" + spfhdrid + " ]";
    }
    
}
