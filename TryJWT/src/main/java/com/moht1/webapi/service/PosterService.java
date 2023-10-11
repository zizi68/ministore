package com.moht1.webapi.service;

import java.util.List;

import com.moht1.webapi.model.Poster;

public interface PosterService {
//	public List<PosterDTO> getListPosterDTO();
	public List<Poster> getListPoster();
	public Poster getPosterById(Integer id);
	public Poster savePoster(Poster poster);
	public void deletePoster(Integer id);
}
