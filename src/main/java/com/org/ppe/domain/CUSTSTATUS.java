package com.org.ppe.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CUSTSTATUS.
 */
@Entity
@Table(name = "custstatus")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CUSTSTATUS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cust_id", nullable = false)
    private Integer custId;

    @NotNull
    @Column(name = "ppe_status", nullable = false)
    private String ppeStatus;

    @Column(name = "change_dt")
    private LocalDate changeDt;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;
    
   

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustId() {
        return custId;
    }

    public CUSTSTATUS custId(Integer custId) {
        this.custId = custId;
        return this;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getPpeStatus() {
        return ppeStatus;
    }

    public CUSTSTATUS ppeStatus(String ppeStatus) {
        this.ppeStatus = ppeStatus;
        return this;
    }

    public void setPpeStatus(String ppeStatus) {
        this.ppeStatus = ppeStatus;
    }

    public LocalDate getChangeDt() {
        return changeDt;
    }

    public CUSTSTATUS changeDt(LocalDate changeDt) {
        this.changeDt = changeDt;
        return this;
    }

    public void setChangeDt(LocalDate changeDt) {
        this.changeDt = changeDt;
    }

    public String getUserId() {
        return userId;
    }

    public CUSTSTATUS userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CUSTSTATUS cUSTSTATUS = (CUSTSTATUS) o;
        if (cUSTSTATUS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cUSTSTATUS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CUSTSTATUS{" +
            "id=" + getId() +
            ", custId=" + getCustId() +
            ", ppeStatus='" + getPpeStatus() + "'" +
            ", changeDt='" + getChangeDt() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
