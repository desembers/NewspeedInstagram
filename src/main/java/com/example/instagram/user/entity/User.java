package com.example.instagram.user.entity;

import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)   // JPA 프록시/`리플렉션`을 위해 기본 생성자(Protected 이상) 필수
@Entity                                                     // JPA가 이 클래스를 테이블과 매핑하도록 표시
@Table(name = "users")                                      // 테이블명을 명시해 스키마 충돌 및 가독성 개선
public class User extends BaseEntity {                      // 생성/수정 시각 공통 필드(BaseEntity) 를 상속

    // User  비주인    / 부모 역할
    // Profile 주인   / 자식, PK=FK 공유

    @Id                                                     // 기본키(PK) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // MySQL 의 AUTO_INCREMENT 전략과 호환
    private Long id;

    // erDiagram 요구사항 :  / NOT NULL + 길이 제한으로 저장공간/유효성 1차 방어
    @Column(nullable = false, unique = true, length = 30)           // 사용자 이름 동명이인 가능성 UK는 피하기
    private String userName;

    // 로그인 식별 이메일은 중복 불가 UK
    @Column(nullable = false, unique = true, length = 255)          // 사용자 이메일 UK
    private String email;

    // 해시(bcrypt)된 비밀번호 저장(평문 금지). 길이는 해시 문자열 길이에 맞춤
    @Column(nullable = false, length = 100)
    private String password;

    // 도메인 규칙을 강제하기 위해 의미있는 생성자 제공(필수 필드만을 구성)
    public User(String userName,
                String email,
                String password
    ) {
        this.userName = userName;
        this.email    = email;
        this.password = password;
    }

    /**
     * 공유 PK 1:1(항상 존재) 패턴의 부모 쪽 관계.
     * - mappedBy="user": FK를 가진 쪽(자식, Profile)이 연관관계의 주인
     * - cascade=ALL: 부모(User) 영속성 전이를 통해 자식(Profile)도 함께 persist/merge/remove
     * - orphanRemoval=true: 부모에서 자식을 끊으면(참조 null) DB에서도 자동 삭제
     * - optional=false: 도메인 의도(항상 존재)를 모델에 표현
     */
    @OneToOne(mappedBy = "user",
            fetch = FetchType.LAZY,       // 1:1에서는 Lazy 불필요, 자동으로 eager로 설정.
//            cascade = CascadeType.ALL,  // 엔티티 인식 문제로 우선 주석 처리!
//            orphanRemoval = true,
            optional = false)
    private Profile profile;

    /**
     * 편의 메서드: 양방향 연결 일관성 유지
     */
    private void setProfile(Profile p) {
        // 기존 연관 끊기
        if (this.profile != null && this.profile.getUser() == this) {
            this.profile.setUser(null);
        }
        this.profile = p;
        if (p != null && p.getUser() != this) {
            p.setUser(this);
        }
    }

    /**
     * 편의 메서드: 프로필 제거(고아 제거 트리거)
     */
    public void removeProfile() {
        setProfile(null);                       // orphanRemoval=true → profiles 행 삭제
    }

    // 변경 로직을 엔티티 내부로 캡슐화(Setter 남용 방지)
    public void update(String username,
                       String email,
                       String password
    ) {
        this.userName = username;
        this.email = email;
        this.password = password;
    }
}