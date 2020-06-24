package com.sms.model;

import java.time.LocalDateTime;

public class MessageRequestModel {

	Long id;

	LocalDateTime createdTs;

	LocalDateTime modifiedTs;

	boolean active;

	Long sentCount;

	Long failCount;

	LocalDateTime scheduledTime;

	Long messageId;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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
		MessageRequestModel other = (MessageRequestModel) obj;
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
		return "MessageRequestModel [id=" + id + ", createdTs=" + createdTs + ", modifiedTs=" + modifiedTs + ", active="
				+ active + ", sentCount=" + sentCount + ", failCount=" + failCount + ", scheduledTime=" + scheduledTime
				+ "]";
	}

}