package com.sms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "request_log")
public class RequestLogDomain implements Serializable {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	Long contactid;
	@Id
	@Column
	Long requestid;

	@JsonIgnore
	public Long getContactid() {
		return contactid;
	}

	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}

	public Long getRequestid() {
		return requestid;
	}

	public void setRequestid(Long requestid) {
		this.requestid = requestid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contactid == null) ? 0 : contactid.hashCode());
		result = prime * result + ((requestid == null) ? 0 : requestid.hashCode());
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
		RequestLogDomain other = (RequestLogDomain) obj;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		if (requestid == null) {
			if (other.requestid != null)
				return false;
		} else if (!requestid.equals(other.requestid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestLogDomain [contactid=" + contactid + ", requestid=" + requestid + "]";
	}

}
