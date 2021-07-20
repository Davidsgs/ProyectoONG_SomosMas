package com.restteam.ong.services.impl;

import javax.transaction.Transactional;

import com.restteam.ong.controllers.dto.NewsPageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.restteam.ong.controllers.dto.NewsDTO;
import com.restteam.ong.models.Categories;
import com.restteam.ong.models.News;
import com.restteam.ong.repositories.NewsRepository;
import com.restteam.ong.services.CategoriesService;
import com.restteam.ong.services.NewsService;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.stream.Collectors;

@Service

public class NewsServicelmpl implements NewsService {

    private static final int PAGE_SIZE = 10 ;
    private static final String URL_NEWS_PAGINATION_SERVICE = "/news?page=";

    @Autowired
	NewsRepository newsRepository;

	@Autowired
	CategoriesService categoriesService;
	ModelMapper modelMapper = new ModelMapper();

	@Override
	public News postNews(NewsDTO newsDTO) {
		News news = new News();
		Categories categories;
		if(!categoriesService.existCategoryByName(newsDTO.getCategoryRequest().getName())){
			categories = categoriesService.postCategory(newsDTO.getCategoryRequest());
		}else{
			categories = categoriesService.getCategoriesByName(newsDTO.getCategoryRequest().getName());
		}

		modelMapper.map(newsDTO, news);
		news.setRegDate(new Date().getTime() / 1000);
		news.setUpDateDate(news.getRegDate());
		news.setCategories(categories);
		return newsRepository.save(news);

	}

	@Override
	public String deleteNewsById(Long id) {
		try {
			newsRepository.deleteById(id);
			return "La noticia se elimino con la id : " + id;
		} catch (Exception error) {
			return "No existe la noticia con esa id, ingrese otro";
		}
	}

	@Transactional
	public News updateNews(NewsDTO newsDTO, Long id) {
		var newsToUpdate = getNewsById(id);
		Categories categories;
		if(!categoriesService.existCategoryByName(newsDTO.getCategoryRequest().getName())){
			categories = categoriesService.postCategory(newsDTO.getCategoryRequest());
		}else{
			categories = categoriesService.getCategoriesByName(newsDTO.getCategoryRequest().getName());
		}
		newsToUpdate.setCategories(categories);
		if (newsDTO.getContent() != null && !newsDTO.getContent().isBlank()) {
			newsToUpdate.setContent(newsDTO.getContent());
		}
		if (newsDTO.getName() != null && !newsDTO.getName().isBlank()) {
			newsToUpdate.setName(newsDTO.getName());
		}
		if (newsDTO.getImage() != null && !newsDTO.getImage().isBlank()) {
			newsToUpdate.setImage(newsDTO.getImage());
		}
		// Se agrega la ultima vez que fue actualizado.
		newsToUpdate.setUpDateDate(System.currentTimeMillis() / 1000);
		return newsToUpdate;
	}

	@Override
	public News getNewsByName(String name) {
		return newsRepository.findByName(name).orElseThrow(() -> new IllegalStateException(String.format("News with name: %s not found.", name)));
	}

	@Override
	public Boolean existId(Long id) {
		return newsRepository.existsById(id);
	}

	@Override
	public News getNewsById(Long id) {
		return newsRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("News with ID %n doesn't exist %s", id)));
	}

	@Override
	public NewsDTO getNewsDTO(Long id) {
		var newsDTO = new NewsDTO();
		try {
			var news = this.getNewsById(id);
			newsDTO.setName(news.getName());
			newsDTO.setImage(news.getImage());
			newsDTO.setContent(news.getContent());
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		return newsDTO;
	}

	@Override
	public News findByNameOrElseCreateNewNews(NewsDTO newsDTO) {
		var modelMapper = new ModelMapper();
		var news = newsRepository.findByName(newsDTO.getName()).orElse(modelMapper.map(newsDTO, News.class));
		news.setCategories( categoriesService.getCategoriesByNameOrCreateNew(newsDTO.getCategoryRequest()));
		return news;
	}
    @Override
    public NewsPageDTO getAll(Integer page) {
        var modelMapper = new ModelMapper();
        Page<News> news = newsRepository.findAll(PageRequest.of(page, PAGE_SIZE));
        if(CollectionUtils.isEmpty(news.getContent())){
            throw new IllegalStateException("no hay datos para mostrar");
        }
        NewsPageDTO newPage = new NewsPageDTO();
        newPage.setElements(news.get().map(n -> modelMapper.map(n, NewsDTO.class)).collect(Collectors.toList()));
        newPage.setCurrentPage(page);
        if (news.hasNext()) {
            newPage.setNextUrl(URL_NEWS_PAGINATION_SERVICE + (page + 1));
        }
        if (news.hasPrevious()) {
            newPage.setPreviousUrl(URL_NEWS_PAGINATION_SERVICE +(page-1));
        }
        newPage.setTotalElements(news.getTotalElements());

        return newPage;
    }
}
