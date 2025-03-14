package com.sati.model;
// Generated 25 oct. 2023, 22:30:48 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SourceFinancement generated by hbm2java
 */
@Entity
@Table(name = "source_financement", catalog = "sygep_bd")
public class SourceFinancement implements java.io.Serializable {

	private Integer idSource;
	private String codeSource;
	private String libSource;
	private String descriptionSource;
	private Set<Entree> entrees = new HashSet<Entree>(0);

	public SourceFinancement() {
	}

	public SourceFinancement(String codeSource, String libSource, String descriptionSource, Set<Entree> entrees) {
		this.codeSource = codeSource;
		this.libSource = libSource;
		this.descriptionSource = descriptionSource;
		this.entrees = entrees;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID_SOURCE", unique = true, nullable = false)
	public Integer getIdSource() {
		return this.idSource;
	}

	public void setIdSource(Integer idSource) {
		this.idSource = idSource;
	}

	@Column(name = "CODE_SOURCE", length = 10)
	public String getCodeSource() {
		return this.codeSource;
	}

	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}

	@Column(name = "LIB_SOURCE", length = 50)
	public String getLibSource() {
		return this.libSource;
	}

	public void setLibSource(String libSource) {
		this.libSource = libSource;
	}

	@Column(name = "DESCRIPTION_SOURCE", length = 65535)
	public String getDescriptionSource() {
		return this.descriptionSource;
	}

	public void setDescriptionSource(String descriptionSource) {
		this.descriptionSource = descriptionSource;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceFinancement")
	public Set<Entree> getEntrees() {
		return this.entrees;
	}

	public void setEntrees(Set<Entree> entrees) {
		this.entrees = entrees;
	}

}
