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
@Table(name = "Assumptions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assumptions.findAll", query = "SELECT a FROM Assumptions a")
    , @NamedQuery(name = "Assumptions.findByAssId", query = "SELECT a FROM Assumptions a WHERE a.assId = :assId")
    , @NamedQuery(name = "Assumptions.findByName", query = "SELECT a FROM Assumptions a WHERE a.name = :name")
    , @NamedQuery(name = "Assumptions.findByDescription", query = "SELECT a FROM Assumptions a WHERE a.description = :description")
    , @NamedQuery(name = "Assumptions.findByValue", query = "SELECT a FROM Assumptions a WHERE a.value = :value")
    , @NamedQuery(name = "Assumptions.findByEquations", query = "SELECT a FROM Assumptions a WHERE a.equations = :equations")})
public class Assumptions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assId")
    private Integer assId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Value")
    private Double value;
    @Column(name = "equations")
    private String equations;
    @JoinColumn(name = "reference", referencedColumnName = "refid")
    @ManyToOne(optional = false)
    private Datarefer reference;
    @JoinColumn(name = "project_id", referencedColumnName = "pro_id")
    @ManyToOne(optional = false)
    private TclProjects projectId;

    public Assumptions() {
    }

    public Assumptions(Integer assId) {
        this.assId = assId;
    }

    public Integer getAssId() {
        return assId;
    }

    public void setAssId(Integer assId) {
        this.assId = assId;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getEquations() {
        return equations;
    }

    public void setEquations(String equations) {
        this.equations = equations;
    }

    public Datarefer getReference() {
        return reference;
    }

    public void setReference(Datarefer reference) {
        this.reference = reference;
    }

    public TclProjects getProjectId() {
        return projectId;
    }

    public void setProjectId(TclProjects projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assId != null ? assId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assumptions)) {
            return false;
        }
        Assumptions other = (Assumptions) object;
        if ((this.assId == null && other.assId != null) || (this.assId != null && !this.assId.equals(other.assId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.topfield.modal.Assumptions[ assId=" + assId + " ]";
    }
    
}
