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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Murali
 */
@Entity
@Table(name = "ccf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ccf.findAll", query = "SELECT c FROM Ccf c")
    , @NamedQuery(name = "Ccf.findByCcfId", query = "SELECT c FROM Ccf c WHERE c.ccfId = :ccfId")
    , @NamedQuery(name = "Ccf.findBySubCategory", query = "SELECT c FROM Ccf c WHERE c.subCategory = :subCategory")
    , @NamedQuery(name = "Ccf.findByDescription", query = "SELECT c FROM Ccf c WHERE c.description = :description")
    , @NamedQuery(name = "Ccf.findByAchievement", query = "SELECT c FROM Ccf c WHERE c.achievement = :achievement")
    , @NamedQuery(name = "Ccf.findByAchievementPercentage", query = "SELECT c FROM Ccf c WHERE c.achievementPercentage = :achievementPercentage")
    , @NamedQuery(name = "Ccf.findByComments", query = "SELECT c FROM Ccf c WHERE c.comments = :comments")})
public class Ccf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ccfId")
    private Integer ccfId;
    @Column(name = "SubCategory")
    private String subCategory;
    @Column(name = "Description")
    private String description;
    @Column(name = "Achievement")
    private String achievement;
    @Column(name = "Achievement_Percentage")
    private String achievementPercentage;
    @Column(name = "Comments")
    private String comments;
    @JoinColumn(name = "ccfHdr", referencedColumnName = "spfhdrid")
    @ManyToOne(optional = false)
    private Spfhdr ccfHdr;

    public Ccf() {
    }

    public Ccf(Integer ccfId) {
        this.ccfId = ccfId;
    }

    public Integer getCcfId() {
        return ccfId;
    }

    public void setCcfId(Integer ccfId) {
        this.ccfId = ccfId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getAchievementPercentage() {
        return achievementPercentage;
    }

    public void setAchievementPercentage(String achievementPercentage) {
        this.achievementPercentage = achievementPercentage;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Spfhdr getCcfHdr() {
        return ccfHdr;
    }

    public void setCcfHdr(Spfhdr ccfHdr) {
        this.ccfHdr = ccfHdr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccfId != null ? ccfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ccf)) {
            return false;
        }
        Ccf other = (Ccf) object;
        if ((this.ccfId == null && other.ccfId != null) || (this.ccfId != null && !this.ccfId.equals(other.ccfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Ccf[ ccfId=" + ccfId + " ]";
    }
    
}
