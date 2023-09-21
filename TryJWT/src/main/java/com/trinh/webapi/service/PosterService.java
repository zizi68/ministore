package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.dto.PosterDTO;
import com.trinh.webapi.model.Poster;

public interface PosterService {
//	public List<PosterDTO> getListPosterDTO();
	public List<Poster> getListPoster();
	public Poster getPosterById(Integer id);
	public Poster savePoster(Poster poster);
	public void deletePoster(Integer id);
}
