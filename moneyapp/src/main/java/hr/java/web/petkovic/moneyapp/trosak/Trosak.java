package hr.java.web.petkovic.moneyapp.trosak;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="trosak")
public class Trosak implements Serializable
{

	private static final long serialVersionUID = -8061231562690743569L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="createdate")
	private LocalDateTime createDate;

	@Column(name="naziv")
	@NotNull(message="{msg.validation.expenseName}")
	@Size(min = 2, max = 30, message="{msg.validation.expenseName.length}")
	private String naziv;

	@Column(name="iznos")
	@NotNull(message="{msg.validation.expenseAmount}")
	@Min(value = 20, message="{msg.validation.expenseAmount.min}")
	private Double iznos;

	@Enumerated(EnumType.STRING)
	@Column(name="vrstatroska", length=20)
	@NotNull(message="{msg.validation.expenseType}")
	private VrstaTroska vrstaTroska;

	@Column(name="novcanik_id")
	@JoinTable(name="novcanik",
	joinColumns = @JoinColumn(name="id"))
	private Long novcanikId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String _naziv) {
		this.naziv = _naziv;
	}

	public Double getIznos() {
		return iznos;
	}

	public void setIznos(Double _iznos) {
		this.iznos = _iznos;
	}

	public VrstaTroska getVrstaTroska() {
		return vrstaTroska;
	}

	public void setVrstaTroska(VrstaTroska _vrstaTroska) {
		this.vrstaTroska = _vrstaTroska;
	}

	public Long getNovcanikId() {
		return novcanikId;
	}

	public void setNovcanikId(Long novcanikId) {
		this.novcanikId = novcanikId;
	}
	
	public Trosak() {
		this.createDate = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iznos == null) ? 0 : iznos.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((novcanikId == null) ? 0 : novcanikId.hashCode());
		result = prime * result + ((vrstaTroska == null) ? 0 : vrstaTroska.hashCode());
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
		Trosak other = (Trosak) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (iznos == null) {
			if (other.iznos != null)
				return false;
		} else if (!iznos.equals(other.iznos))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (novcanikId == null) {
			if (other.novcanikId != null)
				return false;
		} else if (!novcanikId.equals(other.novcanikId))
			return false;
		if (vrstaTroska != other.vrstaTroska)
			return false;
		return true;
	}

	public static enum VrstaTroska {
		Hrana, Rezije, Ostalo
	}


}
