package hr.java.web.petkovic.moneyapp.trosak;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Trosak
{
	private Long id;
	private LocalDateTime createDate;

	@NotNull(message = "Polje Naziv ne smije biti prazno!")
	@Size(min = 2, max = 30, message = "Naziv mora biti između 2 i 30 znakova!")
	private String naziv;

	@NotNull(message = "Polje Iznos ne smije biti prazno!")
	@Min(value = 20, message = "Iznos mora biti veći od 20!")
	private Double iznos;

	@NotNull(message = "Vrsta Troška mora biti odabrana!")
	private VrstaTroska vrstaTroska;

	private Long novcanikId;
	private Novcanik novcanik;

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

	public Novcanik getNovcanik() {
		return novcanik;
	}

	public void setNovcanik(Novcanik novcanik) {
		this.novcanik = novcanik;
	}
	
	public Trosak() {
		this.createDate = LocalDateTime.now();
	}

	public static enum VrstaTroska {
		Hrana, Režije, Ostalo
	}


}
