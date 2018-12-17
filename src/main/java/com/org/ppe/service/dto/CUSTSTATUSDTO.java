package com.org.ppe.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CUSTSTATUS entity.
 */
public class CUSTSTATUSDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer custId;

    @NotNull
    private String ppeStatus;

    private LocalDate changeDt;

    @NotNull
    private String userId;
    
    private Integer days;
    
  

    public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getPpeStatus() {
        return ppeStatus;
    }

    public void setPpeStatus(String ppeStatus) {
        this.ppeStatus = ppeStatus;
    }

    public LocalDate getChangeDt() {
        return changeDt;
    }

    public void setChangeDt(LocalDate changeDt) {
        this.changeDt = changeDt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CUSTSTATUSDTO cUSTSTATUSDTO = (CUSTSTATUSDTO) o;
        if (cUSTSTATUSDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cUSTSTATUSDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CUSTSTATUSDTO{" +
            "id=" + getId() +
            ", custId=" + getCustId() +
            ", ppeStatus='" + getPpeStatus() + "'" +
            ", changeDt='" + getChangeDt() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
