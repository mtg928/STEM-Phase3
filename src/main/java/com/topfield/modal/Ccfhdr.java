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
@Table(name = "ccfhdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ccfhdr.findAll", query = "SELECT c FROM Ccfhdr c")
    , @NamedQuery(name = "Ccfhdr.findByCcfhdrId", query = "SELECT c FROM Ccfhdr c WHERE c.ccfhdrId = :ccfhdrId")
    , @NamedQuery(name = "Ccfhdr.findByMainCategory", query = "SELECT c FROM Ccfhdr c WHERE c.mainCategory = :mainCategory")
    , @NamedQuery(name = "Ccfhdr.findByDescription", query = "SELECT c FROM Ccfhdr c WHERE c.description = :description")
    , @NamedQuery(name = "Ccfhdr.findByAscore", query = "SELECT c FROM Ccfhdr c WHERE c.ascore = :ascore")
    , @NamedQuery(name = "Ccfhdr.findByBscore", query = "SELECT c FROM Ccfhdr c WHERE c.bscore = :bscore")
    , @NamedQuery(name = "Ccfhdr.findByRowscore", query = "SELECT c FROM Ccfhdr c WHERE c.rowscore = :rowscore")
    , @NamedQuery(name = "Ccfhdr.findByBetaFactor", query = "SELECT c FROM Ccfhdr c WHERE c.betaFactor = :betaFactor")
    , @NamedQuery(name = "Ccfhdr.findByDiagnosticCoverage", query = "SELECT c FROM Ccfhdr c WHERE c.diagnosticCoverage = :diagnosticCoverage")
    , @NamedQuery(name = "Ccfhdr.findByComments", query = "SELECT c FROM Ccfhdr c WHERE c.comments = :comments")
    , @NamedQuery(name = "Ccfhdr.findByCreateddate", query = "SELECT c FROM Ccfhdr c WHERE c.createddate = :createddate")})
public class Ccfhdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ccfhdrId")
    private Integer ccfhdrId;
    @Column(name = "Main_Category")
    private String mainCategory;
    @Column(name = "Description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Ascore")
    private Double ascore;
    @Column(name = "Bscore")
    private Double bscore;
    @Column(name = "Rowscore")
    private Double rowscore;
    @Column(name = "betaFactor")
    private Double betaFactor;
    @Column(name = "DiagnosticCoverage")
    private Double diagnosticCoverage;
    @Column(name = "Comments")
    private String comments;
    @Basic(optional = false)
    @Column(name = "Createddate")
    @Temporal(TemporalType.DATE)
    private Date createddate;
    @JoinColumn(name = "ccf_SubComponent", referencedColumnName = "spg_id")
    @ManyToOne(optional = false)
    private SubProductGroup ccfSubComponent;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;
    @JoinColumn(name = "user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Users user;

    public Ccfhdr() {
    }

    public Ccfhdr(Integer ccfhdrId) {
        this.ccfhdrId = ccfhdrId;
    }

    public Ccfhdr(Integer ccfhdrId, Date createddate) {
        this.ccfhdrId = ccfhdrId;
        this.createddate = createddate;
    }

    public Integer getCcfhdrId() {
        return ccfhdrId;
    }

    public void setCcfhdrId(Integer ccfhdrId) {
        this.ccfhdrId = ccfhdrId;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAscore() {
        return ascore;
    }

    public void setAscore(Double ascore) {
        this.ascore = ascore;
    }

    public Double getBscore() {
        return bscore;
    }

    public void setBscore(Double bscore) {
        this.bscore = bscore;
    }

    public Double getRowscore() {
        return rowscore;
    }

    public void setRowscore(Double rowscore) {
        this.rowscore = rowscore;
    }

    public Double getBetaFactor() {
        return betaFactor;
    }

    public void setBetaFactor(Double betaFactor) {
        this.betaFactor = betaFactor;
    }

    public Double getDiagnosticCoverage() {
        return diagnosticCoverage;
    }

    public void setDiagnosticCoverage(Double diagnosticCoverage) {
        this.diagnosticCoverage = diagnosticCoverage;
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

    public SubProductGroup getCcfSubComponent() {
        return ccfSubComponent;
    }

    public void setCcfSubComponent(SubProductGroup ccfSubComponent) {
        this.ccfSubComponent = ccfSubComponent;
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
        hash += (ccfhdrId != null ? ccfhdrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ccfhdr)) {
            return false;
        }
        Ccfhdr other = (Ccfhdr) object;
        if ((this.ccfhdrId == null && other.ccfhdrId != null) || (this.ccfhdrId != null && !this.ccfhdrId.equals(other.ccfhdrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Ccfhdr[ ccfhdrId=" + ccfhdrId + " ]";
    }
    
}
