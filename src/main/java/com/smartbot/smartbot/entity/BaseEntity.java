package com.smartbot.smartbot.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = -93985908393534941L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  protected Long id;
  protected String uuid;
  protected boolean deleted;
  @Column(name = "row_creation_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar rowCreationTime;
  @Column(name = "row_modification_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar rowModificationTime;

  @PreUpdate
  public void preUpdate() {
    this.rowModificationTime = Calendar.getInstance();
  }

  public BaseEntity() {
    super();
  }

  public Long getId() {
    return id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuId) {
    this.uuid = uuId;
  }

  @PrePersist
  public void prePersist() {
    if (this.uuid == null) {
      this.uuid = UUID.randomUUID().toString();
      Calendar now = Calendar.getInstance();
      this.rowCreationTime = now;
      this.rowModificationTime = now;
    }
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (this.getId() != null ? this.getId().hashCode() : 0);

    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object)
      return true;
    if (object == null)
      return false;
    if (getClass() != object.getClass())
      return false;

    BaseEntity other = (BaseEntity) object;
    if (this.getId() != other.getId() && (this.getId() == null || !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  public Calendar getRowCreationTime() {
    return rowCreationTime;
  }

  public void setRowCreationTime(Calendar rowCreationTime) {
    this.rowCreationTime = rowCreationTime;
  }

  public Calendar getRowModificationTime() {
    return rowModificationTime;
  }

  public void setRowModificationTime(Calendar rowModificationTime) {
    this.rowModificationTime = rowModificationTime;
  }

}
