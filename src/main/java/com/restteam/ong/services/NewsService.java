package com.restteam.ong.services;

import com.restteam.ong.controllers.dto.NewsPageDTO;
import org.springframework.stereotype.Service;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.News;

@Service
public interface NewsService {

	public News postNews(NewsDTO newsDTO);

	public String deleteNewsById(Long id);

	public News updateNews(NewsDTO news, Long id);

	public Boolean existId(Long id);

	public News getNewsById(Long id);

	public News getNewsByName(String name);

	public News findByNameOrElseCreateNewNews(NewsDTO newsDTO);

	public NewsDTO getNewsDTO(Long id);

	public NewsPageDTO getAll(Integer page);

}
