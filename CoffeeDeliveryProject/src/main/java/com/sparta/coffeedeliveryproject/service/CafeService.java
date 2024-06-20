package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import org.springframework.stereotype.Service;

@Service
public class CafeService {

    private final CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }


}
