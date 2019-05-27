package hr.java.web.petkovic.moneyapp.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import hr.java.web.petkovic.moneyapp.trosak.Novcanik;
import hr.java.web.petkovic.moneyapp.trosak.Trosak;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTest {

	private MultiValueMap<String, String> trosakMap = new LinkedMultiValueMap<>();

	private MultiValueMap<String, String> novcanikMap = new LinkedMultiValueMap<>();

	@Autowired
	private MockMvc mockMvc;
	
	private MultiValueMap<String, String> generateTrosak(MultiValueMap<String, String> map) {
		map.add("naziv", "testTrosak");
		map.add("vrstaTroska",Trosak.VrstaTroska.Hrana.toString());
		map.add("novcanikId", "1");
		map.add("iznos", "1000");

		return map;
	}
	
	@Test
	public void getTrosakTest() throws Exception
	{
		this.mockMvc
		.perform(get("/api/trosak/2").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Trosak")));
	}

	@Test
	public void postTrosakTest() throws Exception
	{
		this.mockMvc
		.perform(post("/api/trosak").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()).params(generateTrosak(trosakMap)).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void putTrosakTest() throws Exception
	{
		this.mockMvc
		.perform(put("/api/trosak/1").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()).params(generateTrosak(trosakMap)).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void deleteTrosakTest() throws Exception
	{
		this.mockMvc
		.perform(delete("/api/trosak/1").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()))
		.andExpect(status().isNoContent());
	}

	private MultiValueMap<String, String> generateNovcanik(MultiValueMap<String, String> map) {
		map.add("ime123", "testNovcanik");
		map.add("tipNovcanika",Novcanik.TipNovcanika.Ostalo.toString());
		return map;
	}

	@Test
	public void getNovcanikTest() throws Exception
	{
		this.mockMvc
		.perform(get("/api/novcanik/3").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("oper")));
	}

//	@Test
//	public void postNovcanikTest() throws Exception
//	{
//		this.mockMvc
//		.perform(post("/api/novcanik").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()).params(generateNovcanik(novcanikMap)).contentType(MediaType.APPLICATION_JSON_VALUE))
//		.andExpect(status().is2xxSuccessful());
//	}
//
//	@Test
//	public void putNovcanikTest() throws Exception
//	{
//		this.mockMvc
//		.perform(put("/api/novcanik/5").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()).params(generateNovcanik(novcanikMap)).contentType(MediaType.APPLICATION_JSON_VALUE))
//		.andExpect(status().is2xxSuccessful());
//	}
//
//	@Test
//	public void deleteNovcanikTest() throws Exception
//	{
//		this.mockMvc
//		.perform(delete("/api/novcanik/3").with(user("admin").password("admin").roles("USER", "ADMIN")).with(csrf()))
//		.andExpect(status().isNoContent());
//	}
}
