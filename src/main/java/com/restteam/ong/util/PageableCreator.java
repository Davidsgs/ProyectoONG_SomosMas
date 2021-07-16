package com.restteam.ong.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableCreator {


    public Pageable createPaginationRequest(int page, int size){
        return PageRequest.of(page, size);
    }
}
