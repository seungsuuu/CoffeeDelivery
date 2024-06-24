package com.sparta.coffeedeliveryproject.service;

import com.sparta.coffeedeliveryproject.dto.CafeEditRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeRequestDto;
import com.sparta.coffeedeliveryproject.dto.CafeResponseDto;
import com.sparta.coffeedeliveryproject.dto.MessageResponseDto;
import com.sparta.coffeedeliveryproject.entity.Cafe;
import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.repository.CafeRepository;
import com.sparta.coffeedeliveryproject.repository.UserRepository;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCafeService {

    private final CafeRepository cafeRepository;
    private final UserRepository userRepository;

    public CafeResponseDto createCafe(CafeRequestDto requestDto) {

        String cafeName = requestDto.getCafeName();
        String cafeInfo = requestDto.getCafeInfo();
        String cafeAddress = requestDto.getCafeAddress();

        // 중복된 cafeName 있는지 확인
        if (cafeRepository.findByCafeName(cafeName).isPresent()) {
            throw new IllegalArgumentException("이미 작성된 카페입니다.");
        }

        Cafe cafe = new Cafe(cafeName, cafeInfo, cafeAddress);

        Cafe SaveCafe = cafeRepository.save(cafe);

        return new CafeResponseDto(SaveCafe);
    }

    public List<CafeResponseDto> getAllCafe(int page) {

        Sort sort = Sort.by(Sort.Direction.ASC, "cafeId");
        Pageable pageable = PageRequest.of(page, 5, sort);
        Page<CafeResponseDto> CafePage = cafeRepository.findAll(pageable).map(CafeResponseDto::new);
        List<CafeResponseDto> responseDtoList = CafePage.getContent();

        if (responseDtoList.isEmpty()) {
            throw new IllegalArgumentException("작성된 카페 페이지가 없거나, 입력된 " + (page + 1) + " 페이지에 글이 없습니다.");
        }

        return responseDtoList;
    }

    @Transactional
    public CafeResponseDto editCafe(Long cafeId, CafeEditRequestDto requestDto) {

        if (isNullAndEmpty(requestDto.getNewCafeInfo()) && isNullAndEmpty(requestDto.getNewCafeAddress())) {
            throw new IllegalArgumentException("수정으로 요청된 값이 없습니다.");
        }

        Cafe cafe = findCafeById(cafeId);

        if (!isNullAndEmpty(requestDto.getNewCafeInfo())) {
            cafe.editCafeInfo(requestDto.getNewCafeInfo());
        }

        if (!isNullAndEmpty(requestDto.getNewCafeAddress())) {
            cafe.editCafeAddress(requestDto.getNewCafeAddress());
        }

        return new CafeResponseDto(cafe);
    }

    public MessageResponseDto deleteCafe(Long cafeId) {

        Cafe cafe = findCafeById(cafeId);

        cafeRepository.delete(cafe);

        return new MessageResponseDto("삭제 완료 되었습니다.");
    }

    private Cafe findCafeById(Long cafeId) {

        return cafeRepository.findById(cafeId).orElseThrow(
                () -> new IllegalArgumentException("해당 카페 페이지를 찾을 수 없습니다.")
        );
    }

    // String 요청 데이터가 비어 있느지 확인하는 메서드
    private boolean isNullAndEmpty(String string) {
        return string == null || string.isEmpty();
    }

    @Transactional
    public boolean isAdmin(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getUserId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 없습니다."));

        for (UserRole role : user.getUserRoles()) {
            if ("ADMIN".equals(role.getRole())) {
                return true;
            }
        }
        return false;
    }

}
