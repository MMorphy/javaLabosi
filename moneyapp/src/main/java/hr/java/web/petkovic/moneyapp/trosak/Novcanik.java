package hr.java.web.petkovic.moneyapp.trosak;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Novcanik {

	private Long usernameId;
	private Long id;
	private LocalDateTime createDate;	
	private String ime;
	public List<Trosak> listaTroskova = new ArrayList<Trosak>();
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
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Novcanik() {
		this.ime = "VISA Electron";
		this.tipNovcanika = Novcanik.TipNovcanika.Kartica;
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

	public Long getUsernameId() {
		return usernameId;
	}

	public void setUsernameId(Long usernameId) {
		this.usernameId = usernameId;
	}



	public enum TipNovcanika {
		Gotovina,
		Kartica,
		Ostalo
	}
}