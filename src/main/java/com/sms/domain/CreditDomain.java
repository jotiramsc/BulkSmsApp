package com.sms.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "credit")
@SequenceGenerator(name = "credit_seq", initialValue = 1)
public class CreditDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "credit_seq")
	Long id;

	@Column
	Integer availableCredit;

	@Column
	Integer freeCredit;

	@Column
	Integer usedCredit;

	@Column
	LocalDateTime createdTs;
	@Column
	LocalDateTime modifiedTs;

	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(Integer availableCredit) {
		this.availableCredit = availableCredit;
	}

	public Integer getFreeCredit() {
		return freeCredit;
	}

	public void setFreeCredit(Integer freeCredit) {
		this.freeCredit = freeCredit;
	}

	public Integer getUsedCredit() {
		return usedCredit;
	}

	public void setUsedCredit(Integer usedCredit) {
		this.usedCredit = usedCredit;
	}

	public LocalDateTime getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(LocalDateTime createdTs) {
		this.createdTs = createdTs;
	}

	public LocalDateTime getModifiedTs() {
		return modifiedTs;
	}

	public void setModifiedTs(LocalDateTime modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availableCredit == null) ? 0 : availableCredit.hashCode());
		result = prime * result + ((createdTs == null) ? 0 : createdTs.hashCode());
		result = prime * result + ((freeCredit == null) ? 0 : freeCredit.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modifiedTs == null) ? 0 : modifiedTs.hashCode());
		result = prime * result + ((usedCredit == null) ? 0 : usedCredit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditDomain other = (CreditDomain) obj;
		if (availableCredit == null) {
			if (other.availableCredit != null)
				return false;
		} else if (!availableCredit.equals(other.availableCredit))
			return false;
		if (createdTs == null) {
			if (other.createdTs != null)
				return false;
		} else if (!createdTs.equals(other.createdTs))
			return false;
		if (freeCredit == null) {
			if (other.freeCredit != null)
				return false;
		} else if (!freeCredit.equals(other.freeCredit))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modifiedTs == null) {
			if (other.modifiedTs != null)
				return false;
		} else if (!modifiedTs.equals(other.modifiedTs))
			return false;
		if (usedCredit == null) {
			if (other.usedCredit != null)
				return false;
		} else if (!usedCredit.equals(other.usedCredit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CreditDomain [id=" + id + ", availableCredit=" + availableCredit + ", freeCredit=" + freeCredit
				+ ", usedCredit=" + usedCredit + ", createdTs=" + createdTs + ", modifiedTs=" + modifiedTs + "]";
	}

}
