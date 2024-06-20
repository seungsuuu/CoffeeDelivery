package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.MenuRequestDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
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

    public MessageResponseDto deleteMenu(Long menuId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(
                () -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다.")
        );

        menuRepository.delete(menu);

        return new MessageResponseDto("삭제 완료 되었습니다.");
    }
}
