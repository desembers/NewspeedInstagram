package com.example.instagram.service;

import com.example.instagram.profile.dto.request.ProfileSaveRequestDto;
import com.example.instagram.profile.dto.request.ProfileUpdateRequestDto;
import com.example.instagram.profile.dto.response.ProfileResponseDto;
import com.example.instagram.profile.entity.Profile;
import com.example.instagram.profile.repository.ProfileRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        return toDto(profile);
    }

    private ProfileResponseDto toDto(Profile p) {
        return new ProfileResponseDto(
                p.getUserId(), p.getDisplayName(), p.getBio(), p.getWebsite(),
                p.getBirthdate(), p.getCreatedAt(), p.getUpdatedAt()
        );
    }

    private LocalDate parseDate(String s) {

        // 사용 이유 : Null / 빈 문자 허용
        return (s == null || s.isBlank()) ? null : LocalDate.parse(s);     // 삼항연산자
    }
}
