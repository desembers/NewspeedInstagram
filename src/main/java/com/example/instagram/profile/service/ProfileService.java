package com.example.instagram.profile.service;

import com.example.instagram.common.exception.InValidException;
import com.example.instagram.common.exception.InValidPasswordException;
import com.example.instagram.common.exception.InValidPasswordFormatException;
import com.example.instagram.profile.dto.request.ProfileSaveRequestDto;
import com.example.instagram.profile.dto.request.ProfileUpdateRequestDto;
import com.example.instagram.profile.dto.response.ProfileResponseDto;
import com.example.instagram.profile.entity.Profile;
import com.example.instagram.profile.repository.ProfileRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor                                // 불변 의존성 final 주입 + 테스트 용이
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProfileResponseDto create(Long userId, ProfileSaveRequestDto dto) {
        if (profileRepository.existsByUserId(userId)) {             // 프로필과 유저는 1:1 (공유)구조, 프로필 중복 생성 방지
            throw new IllegalArgumentException("이미 프로필이 존재합니다.");
        }

        User user = userRepository.findById(userId)                 // 공유 PK(MapsId)라 실제 User가 있어야 저장 가능
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Profile profile = new Profile(
                user,
                dto.getDisplayName(),
                dto.getBio(),
                dto.getWebsite(),
                parseDate(dto.getBirthdate())
        );
        profileRepository.save(profile);

        //비밀번호가 일치하지 않으면 다시 로그인해주세요 (브라우저가 임의로 비밀번호 치는것 방지)
        if(!profile.getUser().getPassword().equals(user.getPassword())) {
            throw new InValidException("비밀번호가 일치하지 않습니다.");
        }

        return toDto(profile);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto findByUserId(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 존재하지 않습니다."));
        return toDto(profile);
    }

    @Transactional
    public ProfileResponseDto update(Long userId, ProfileUpdateRequestDto dto) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 존재하지 않습니다."));
        profile.update(
                dto.getDisplayName(),
                dto.getBio(),
                dto.getWebsite(),
                parseDate(dto.getBirthdate())
        );

        //비밀번호와 닉네임이 일치하면 예외처리 해주기
        if(profile.getUser().getPassword().equals(dto.getDisplayName())) {
            throw new InValidPasswordException("동일한 비밀번호로 수정하였습니다.");
        }

        //비밀번호 입력해주지 않으면 안전하게 false값(NPE방지)
        if(StringUtils.isBlank(profile.getUser().getPassword())) {
            throw new InValidPasswordException("비밀번호를 입력하지 않았습니다.");
        }

        //비밀번호 입력 형식 최소 8자 이상, 영문 + 숫자 + 특수문자 포함
        if(profile.getUser().getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$")) {
            throw new InValidPasswordFormatException("비밀번호 형식에 맞지 않습니다.");
        }

        return toDto(profile);
    }

    // Service 계층
    @Transactional
    public void deleteByUserId(Long userId) {
        // 1) 프로필 조회 및 검증 // 프로필 중복 존재 확인(없으면 예외)
        if (!profileRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("프로필이 존재하지 않습니다.");
        }

        // 2) 프로필 삭제
        profileRepository.deleteByUserId(userId);
    }

    private ProfileResponseDto toDto(Profile profile) {
        return new ProfileResponseDto(
                profile.getUserId(),
                profile.getDisplayName(),
                profile.getBio(),
                profile.getWebsite(),
                profile.getBirthdate(),
                profile.getCreatedAt(),
                profile.getUpdatedAt()
        );
    }

    private LocalDate parseDate(String string) {

        // 사용 이유 : Null / 빈 문자 허용
        return (string == null || string.isBlank()) ? null : LocalDate.parse(string);     // 삼항연산자
    }
}
