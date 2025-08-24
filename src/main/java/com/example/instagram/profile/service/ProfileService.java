package com.example.instagram.profile.service;

import com.example.instagram.profile.dto.request.ProfileSaveRequestDto;
import com.example.instagram.profile.dto.request.ProfileUpdateRequestDto;
import com.example.instagram.profile.dto.response.ProfileResponseDto;
import com.example.instagram.profile.entity.Profile;
import com.example.instagram.profile.repository.ProfileRepository;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor                                // 불변 의존성 final 주입 + 테스트 용이
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public ProfileResponseDto create(Long userId, ProfileSaveRequestDto dto) {
        if (profileRepository.existsByUserIdAndDeletedFalse(userId)) {             // 프로필과 유저는 1:1 (공유)구조, 프로필 중복 생성 방지
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 프로필이 존재합니다.");    // 에러 코드 409
        }

        User user = userRepository.findById(userId)                 // 공유 PK(MapsId)라 실제 User가 있어야 저장 가능
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."));    // 에러 코드 404

        Profile profile = new Profile(
                user,
                dto.getDisplayName(),
                dto.getBio(),
                dto.getWebsite(),
                parseDate(dto.getBirthdate())
        );
        profileRepository.save(profile);

        //비밀번호가 일치하지 않으면 다시 로그인해주세요 (브라우저가 임의로 비밀번호 치는것 방지)
        if (!profile.getUser().getPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");     // 에러 코드 401
        }
        return toDto(profile);
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto findByUserId(Long userId) {
        Profile profile = profileRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "프로필이 존재하지 않습니다."));    // 에러 코드 404
        return toDto(profile);
    }

    @Transactional
    public ProfileResponseDto update(Long userId, ProfileUpdateRequestDto dto) {
        Profile profile = profileRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "프로필이 존재하지 않습니다."));    // 에러 코드 404
        profile.update(
                dto.getDisplayName(),
                dto.getBio(),
                dto.getWebsite(),
                parseDate(dto.getBirthdate())
        );
        return toDto(profile);
    }

    // Service 계층
    @Transactional
    public void deleteByUserId(Long userId) {
        // 프로필 객체 조회
        Profile profile = profileRepository.findByUserIdAndDeletedFalse(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "프로필이 존재하지 않습니다."));    // 에러 코드 404

        // Soft Delete 적용
        profile.softDelete();
        profileRepository.save(profile);
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
