package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.MenuRequestDto;
import com.sparta.coffeedeliveryproject.dto.MenuResponseDto;
import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.AdminMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/cafes")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @PostMapping("/{cafeId}/menus")
    public ResponseEntity<MenuResponseDto> createMenu(@PathVariable(value = "cafeId") Long cafeId,
                                                      @Valid @RequestBody MenuRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (adminMenuService.isAdmin(userDetails)) {
            MenuResponseDto responseDto = adminMenuService.createMenu(cafeId, requestDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

    @DeleteMapping("/menus/{menuId}")
    public ResponseEntity<MessageResponseDto> deleteMenu(@PathVariable(value = "menuId") Long menuId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (adminMenuService.isAdmin(userDetails)) {
            MessageResponseDto responseDto = adminMenuService.deleteMenu(menuId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } else {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

}
