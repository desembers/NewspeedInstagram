package com.example.instagram.user.service;

import com.example.instagram.common.config.PasswordEncoder;
import com.example.instagram.user.dto.request.UserUpdateRequestDto;
import com.example.instagram.user.dto.response.UserResponseDto;
import com.example.instagram.user.entity.User;
import com.example.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service                                            // 스프링 서비스 계층 빈 등록(트랜잭션/도메인 로직 위치)
@RequiredArgsConstructor                            // 생성자 주입 자동 생성(불변 final / 테스트 용이)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // Bcrypt 해시 책임 분리(테스트 / 교체 용이)

    @Transactional(readOnly = true)                 // 스냅샷 / 더티체킹 최적화
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors
                        .toList());
    }                                               // 스트림 주의 : 대용량 데이터에는 주의.

    @Transactional(readOnly = true)
    public UserResponseDto findOne(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));     // 에러 코드 404
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findSomeOne(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));     // 에러 코드 404
        return toDto(user);
    }

    @Transactional
    public UserResponseDto update(Long userId, UserUpdateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));     // 에러 코드 404

        String encoded = passwordEncoder.encode(dto.getPassword());
        user.update(                                                // 엔티티 메서드로 변경 캡슐화
                dto.getUserName(),
                dto.getEmail(),
                encoded);
        return toDto(user);
    }

    /***
     * [아래 UserResponseDto 로 변환하는 이유]
     * 서비스 내부에서 엔티티(User) 를 그대로 밖으로 내보내지 않고,
     * 응답용 DTO(UserResponseDto) 로 변환해 보안/안정성/유지보수성을 동시에 잡기 위한 안전한 경계(계층 분리) 장치입니다.
     * toDto()는 그 변환 로직을 한 곳에 모아 중복을 없애고 바뀔 때 한 군데 만 고치면 만사 오케이!
     *
     *
     * [엔티티를 그대로 반환하지 않는 이유]
     * 1. 보안
     * User에는 password 같은 민감 정보가 들어있습니다.
     * 엔티티를 그대로 직렬화하면 실수로 노출될 수 있어요.
     * DTO는 노출하고 싶은 필드만 담습니다.
     *
     * 2. 영속성/JPA 이슈 회피
     * 엔티티엔 지연 로딩(LAZY) 연관관계가 많습니다.
     * 컨트롤러에서 JSON 직렬화 시점에 영속성 컨텍스트가 닫혀 있으면 LazyInitializationException이 터지거나, 반대로 열려 있으면 필요 없는 쿼리가 우르르 나갈 수 있어요.
     * DTO는 필요한 값만 즉시 값 타입으로 들고 있으니 안전합니다.
     *
     * 3. 순환 참조(무한 직렬화) 방지
     * User → Profile → User ... 같은 양방향 매핑이 있으면 JSON 직렬화가 무한 루프에 빠지기 쉽습니다.
     * DTO로 끊어주면 해결됩니다.
     *
     * 4. API 스펙 안정성
     * 엔티티 필드가 바뀌면(이름/타입/추가/삭제) 엔티티를 그대로 반환하던 API는 바이너리/스키마 호환성이 깨집니다.
     * DTO를 두면 “DB/도메인 내부 설계 변경 ↔ 외부 API 응답”을 느슨하게 결합할 수 있어요.
     *
     * 5. 표현/포맷 책임 분리
     * 예: LocalDateTime을 ISO 문자열로 바꾸거나, 마스킹 처리, 파생 필드(예: displayName = username + "#"+id) 계산 등 표현 계층의 일을 DTO 변환에서 처리할 수 있습니다.
     *
     * [toDto() 헬퍼 메서드를 두는 직접적인 이유]
     * 1. DRY(중복 제거)
     * save, findOne, findAll 등 여러 메서드에서 같은 변환을 반복하지 않고, 한 군데로 모읍니다.
     *
     * 2. 변경 영향 최소화
     * 응답 스펙이 바뀌면 이 메서드만 바꾸면 됩니다. 실수로 한 군데만 고치는 일을 줄여요.
     *
     * 3. 일관성
     * 어디서 변환하든 항상 같은 규칙으로 매핑됩니다(필드 선택, 포맷, 마스킹 등).
     *
     * 4. 캡슐화
     * private인 이유: 서비스 내부 구현 디테일이므로 외부에서 건드릴 필요가 없습니다. API 표면을 최소화해 변경 여지를 안으로 숨깁니다.
     *
     * 5. 테스트 용이성
     * 단위 테스트에서 toDto()만 따로 검증할 수도 있고, 서비스 결과 검증도 쉬워집니다.
     */

    private UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
