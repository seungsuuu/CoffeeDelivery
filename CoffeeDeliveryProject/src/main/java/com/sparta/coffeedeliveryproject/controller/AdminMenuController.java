package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MenuRequestDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.service.AdminMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cafes")
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    public AdminMenuController(AdminMenuService adminMenuService) {
        this.adminMenuService = adminMenuService;
    }

    @PostMapping("/{cafeId}/menus")
    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable(value = "cafeId") Long cafeId,
                                                      @RequestBody MenuRequestDto requestDto) {

        MenuResponseDto responseDto = adminMenuService.createMenu(cafeId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<MessageResponseDto> deleteMenu(@PathVariable(value = "menuId") Long menuId) {

       MessageResponseDto responseDto = adminMenuService.deleteMenu(menuId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
