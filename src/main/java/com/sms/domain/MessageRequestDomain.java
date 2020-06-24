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

@Entity(name = "message_request")
@SequenceGenerator(name = "message_request_seq", initialValue = 1)
public class MessageRequestDomain {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_request_seq")
	Long id;

	@Column
	LocalDateTime createdTs;
	@Column
	LocalDateTime modifiedTs;

	@Column
	Long sentCount;

	@Column
	Long failCount;

	@Column
	LocalDateTime scheduledTime;

	@Column
	boolean active;

	@Column
	Long messageId;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "request_log", joinColumns = @JoinColumn(name = "requestid", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "contactid", referencedColumnName = "id"))
	private Set<ContactDomain> contacts = new HashSet<>();

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@JsonIgnore
	public Set<ContactDomain> getContacts() {
		return contacts;
	}

	public void setContacts(Set<ContactDomain> contacts) {
		this.contacts = contacts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getSentCount() {
		return sentCount;
	}

	public void setSentCount(Long sentCount) {
		this.sentCount = sentCount;
	}

	public Long getFailCount() {
		return failCount;
	}

	public void setFailCount(Long failCount) {
		this.failCount = failCount;
	}

	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((createdTs == null) ? 0 : createdTs.hashCode());
		result = prime * result + ((failCount == null) ? 0 : failCount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modifiedTs == null) ? 0 : modifiedTs.hashCode());
		result = prime * result + ((scheduledTime == null) ? 0 : scheduledTime.hashCode());
		result = prime * result + ((sentCount == null) ? 0 : sentCount.hashCode());
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
		MessageRequestDomain other = (MessageRequestDomain) obj;
		if (active != other.active)
			return false;
		if (createdTs == null) {
			if (other.createdTs != null)
				return false;
		} else if (!createdTs.equals(other.createdTs))
			return false;
		if (failCount == null) {
			if (other.failCount != null)
				return false;
		} else if (!failCount.equals(other.failCount))
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
		if (scheduledTime == null) {
			if (other.scheduledTime != null)
				return false;
		} else if (!scheduledTime.equals(other.scheduledTime))
			return false;
		if (sentCount == null) {
			if (other.sentCount != null)
				return false;
		} else if (!sentCount.equals(other.sentCount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessageRequestDomain [id=" + id + ", createdTs=" + createdTs + ", modifiedTs=" + modifiedTs
				+ ", sentCount=" + sentCount + ", failCount=" + failCount + ", scheduledTime=" + scheduledTime
				+ ", active=" + active + "]";
	}

}
