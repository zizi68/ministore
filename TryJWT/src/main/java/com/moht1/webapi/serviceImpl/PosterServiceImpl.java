package com.moht1.webapi.serviceImpl;

import com.moht1.webapi.model.Poster;
import com.moht1.webapi.repository.PosterRepository;
import com.moht1.webapi.service.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterServiceImpl implements PosterService {

    @Autowired
    private PosterRepository posterRepository;

//	public List<PosterDTO> getListPosterDTO() {
//		List<Poster> posters = posterRepository.findAll();
//		List<PosterDTO> list = new ArrayList<PosterDTO>();
//		for (Poster poster : posters) {
//			if (!poster.isActive()) {
//				continue;
//			}
//			PosterDTO p = new PosterDTO(poster.getName(), poster.getType());
//			list.add(p);
//		}
//		return list;
//	}

    @Override
    public List<Poster> getListPoster() {
        return posterRepository.findAll();
    }

    @Override
    public Poster savePoster(Poster poster) {
        return posterRepository.save(poster);
    }

    @Override
    public void deletePoster(Integer id) {
        Poster poster = posterRepository.getById(id);
        posterRepository.delete(poster);
    }

    @Override
    public Poster getPosterById(Integer id) {
        return posterRepository.getById(id);
    }
}
