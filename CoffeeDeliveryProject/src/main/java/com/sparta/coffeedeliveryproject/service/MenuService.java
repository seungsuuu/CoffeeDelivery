package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MenuRequestDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.entity.Menu;
import com.sparta.coffeedeliveryproject.repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public MenuResponseDto createMenu(Long cafeId, MenuRequestDto requestDto) {

        String menuName = requestDto.getMenuName();
        Long menuPrice = requestDto.getMenuPrice();
        Menu menu = new Menu(menuName, menuPrice, cafeId);

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponseDto(saveMenu);
    }
}
