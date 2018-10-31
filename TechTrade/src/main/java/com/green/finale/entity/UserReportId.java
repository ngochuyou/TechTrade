package com.green.finale.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserReportId implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "reporter")
	private Account accuser;

	@ManyToOne
	@JoinColumn(name = "targeted_user")
	private Account targetedUser;

	public UserReportId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserReportId(Account accuser, Account targetedUser) {
		super();
		this.accuser = accuser;
		this.targetedUser = targetedUser;
	}

	public Account getAccuser() {
		return accuser;
	}

	public void setAccuser(Account accuser) {
		this.accuser = accuser;
	}

	public Account getTargetedUser() {
		return targetedUser;
	}

	public void setTargetedUser(Account targetedUser) {
		this.targetedUser = targetedUser;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof VoteId))
			return false;

		UserReportId that = (UserReportId) o;

		return Objects.equals(getAccuser(), that.getAccuser())
				&& Objects.equals(getTargetedUser(), that.getTargetedUser());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAccuser(), getTargetedUser());
	}
}
