package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MenuRequestDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cafes")
public class MenuController {

    private final MenuService menuService;

    public  MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/{cafeId}/menus")
    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable(value = "cafeId") Long cafeId,
                                                      @RequestBody MenuRequestDto requestDto) {

        MenuResponseDto responseDto = menuService.createMenu(cafeId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
