package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MenuRequestDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.Menu;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import com.sparta.coffeedeliveryproject.repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminMenuService {

    private final MenuRepository menuRepository;
    private final CafeRepository cafeRepository;

    public AdminMenuService(MenuRepository menuRepository, CafeRepository cafeRepository) {
        this.menuRepository = menuRepository;
        this.cafeRepository = cafeRepository;
    }

    public MenuResponseDto createMenu(Long cafeId, MenuRequestDto requestDto) {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(
                () -> new IllegalArgumentException("해당 카페를 찾을 수 없습니다.")
        );

        String menuName = requestDto.getMenuName();
        Long menuPrice = requestDto.getMenuPrice();
        Menu menu = new Menu(menuName, menuPrice, cafe);

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponseDto(saveMenu);
    }

    public MessageResponseDto deleteMenu(Long menuId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다.")
        );

        menuRepository.delete(menu);

        return new MessageResponseDto("삭제 완료 되었습니다.");
    }

}
