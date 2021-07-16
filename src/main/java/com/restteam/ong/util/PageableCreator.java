package com.restteam.ong.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableCreator {


    public Pageable goToPage(int page){
        return PageRequest.of(page, 10);
    }
}
