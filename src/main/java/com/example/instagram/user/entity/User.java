package com.example.instagram.user.entity;

import com.example.instagram.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)   // JPA 프록시/`리플렉션`을 위해 기본 생성자(Protected 이상) 필수
@Entity                                                     // JPA가 이 클래스를 테이블과 매핑하도록 표시
@Table(name = "users")                                      // 테이블명을 명시해 스키마 충돌 및 가독성 개선
public class User extends BaseEntity {                      // 생성/수정 시각 공통 필드(BaseEntity) 를 상속

    @Id                                                     // 기본키(PK) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // MySQL 의 AUTO_INCREMENT 전략과 호환
    @Column(name = "user_id")
    private Long id;

    // erDiagram 요구사항 : UNIQUE / NOT NULL + 길이 제한으로 저장공간/유효성 1차 방어
    @Column(nullable = false, unique = true, length = 30)           // 사용자 이름 UK
    private String username;

    // 로그인 식별 이메일은 중복 불가
    @Column(nullable = false, unique = true, length = 255)          // 사용자 이메일 UK
    private String email;

    // 해시(bcrypt)된 비밀번호 저장(평문 금지). 길이는 해시 문자열 길이에 맞춤
    @Column(nullable = false, length = 100)
    private String password;

    // 도메인 규칙을 강제하기 위해 의미있는 생성자 제공(필수 필드만을 구성)
    public User(String username,
                String email,
                String password
    ) {
        this.username = username;
        this.email    = email;
        this.password = password;
    }

    ///  follow
    // 연관 엔티티 생성 시 식별자만 참조하고 싶을 때(조회 없이 프록시처럼 사용)
    private User(Long id) {
        this.id = id; }
    public static User fromUserId(Long id) {
        return new User(id); }

    // 변경 로직을 엔티티 내부로 캡슐화(Setter 남용 방지)
    public void update(String username,
                       String email,
                       String password
    ) {
        this.username = username;
        this.email    = email;
        this.password = password;
    }
}
