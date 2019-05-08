package hr.java.web.petkovic.moneyapp.trosak;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "novcanik")
public class Novcanik implements Serializable{

	private static final long serialVersionUID = -5837668174710554103L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name="username_id")
	@OneToOne(targetEntity=User.class, fetch=FetchType.EAGER)
	private User user;

	@Column(name="createdate")
	private LocalDateTime createDate;

	@Column(name="ime")
	private String ime;

	@OneToMany(mappedBy="id", fetch=FetchType.EAGER)
	public List<Trosak> listaTroskova = new ArrayList<Trosak>();

	@Enumerated(EnumType.STRING)
	@Column(name="tipnovcanika", length=20)
	private TipNovcanika tipNovcanika;
	
	public String getIme() {
		return ime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public List<Trosak> getListaTroskova() {
		return listaTroskova;
	}

	public void setListaTroskova(List<Trosak> listaTroskova) {
		this.listaTroskova = listaTroskova;
	}

	public Novcanik() {
	}

	public Novcanik(String naziv, TipNovcanika tipNovcanika) {
		this.ime = naziv;
		this.tipNovcanika = tipNovcanika;
	}

	public void setIme(String naziv) {
		this.ime = naziv;
	}

	public TipNovcanika getTipNovcanika() {
		return tipNovcanika;
	}

	public void setTipNovcanika(TipNovcanika tipNovcanika) {
		this.tipNovcanika = tipNovcanika;
	}

	public enum TipNovcanika {
		Gotovina, Kartica, Ostalo
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ((listaTroskova == null) ? 0 : listaTroskova.hashCode());
		result = prime * result + ((tipNovcanika == null) ? 0 : tipNovcanika.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Novcanik other = (Novcanik) obj;
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
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (listaTroskova == null) {
			if (other.listaTroskova != null)
				return false;
		} else if (!listaTroskova.equals(other.listaTroskova))
			return false;
		if (tipNovcanika != other.tipNovcanika)
			return false;
		return true;
	}
}