package com.sms.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "contact")
@SequenceGenerator(name = "contact_seq", initialValue = 1)
public class ContactDomain {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq")
	Long id;

	@Column
	String contactName;

	@Column
	String contactNumber;

	@Column
	LocalDateTime createdTs;
	@Column
	LocalDateTime modifiedTs;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "contact_group_mapping", joinColumns = @JoinColumn(name = "contactid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "groupid", referencedColumnName = "id"))
	private Set<ContactGroupDomain> groups = new HashSet<>();

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "request_log", joinColumns = @JoinColumn(name = "contactid", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "requestid", referencedColumnName = "id"))
	private Set<MessageRequestDomain> requests = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	@JsonIgnore
	public Set<ContactGroupDomain> getGroups() {
		return groups;
	}

	public void setGroups(Set<ContactGroupDomain> groups) {
		this.groups = groups;
	}
	
	
	public Set<MessageRequestDomain> getRequests() {
		return requests;
	}

	public void setRequests(Set<MessageRequestDomain> requests) {
		this.requests = requests;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result + ((contactNumber == null) ? 0 : contactNumber.hashCode());
		result = prime * result + ((createdTs == null) ? 0 : createdTs.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modifiedTs == null) ? 0 : modifiedTs.hashCode());

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
		ContactDomain other = (ContactDomain) obj;
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName))
			return false;
		if (contactNumber == null) {
			if (other.contactNumber != null)
				return false;
		} else if (!contactNumber.equals(other.contactNumber))
			return false;
		if (createdTs == null) {
			if (other.createdTs != null)
				return false;
		} else if (!createdTs.equals(other.createdTs))
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

		return true;
	}

	@Override
	public String toString() {
		return "ContactDomain [id=" + id + ", contactName=" + contactName + ", contactNumber=" + contactNumber
				+ ", createdTs=" + createdTs + ", modifiedTs=" + modifiedTs + "]";
	}

}
