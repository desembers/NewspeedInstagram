package com.example.instagram.profile.entity;

import com.example.instagram.common.entity.BaseEntity;
import com.example.instagram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
@Table(name = "profiles")                       // 테이블명 명시
public class Profile extends BaseEntity {       // Profile : 주인 @MapsId로 PK를 User의 PK와 공유)

    @Id
    @Column(name = "user_id")                   // 프로필의 PK가 사용자 PK와 동일(공유 PK)
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)           // 1:1 관계, 지연로딩으로 N+1 및 불필요 조인 방지
    @MapsId                                     // 연관 엔티티(USER)의 PK를 이 엔티티의 PK로 ''공유'' (user_id = PK = FK)
    @JoinColumn(name = "user_id")               // 외래키 컬럼명 명시
    private User user;

    @Column(length = 50)
    private String displayName;                 // 공개용 닉네임 (로그인 아이디와 분리)

    /**
     * @Lob
     * @Lob 어노테이션은 데이터베이스에서 CLOB(Character Large Object) 또는 BLOB(Binary Large Object)과 같은 대용량 데이터를 처리하기 위해 사용됩니다.
     * 일반적인 문자열이나 바이트 배열보다 크기가 큰 데이터를 저장해야 할 때 유용합니다.
     * 예를 들어, 이미지나 문서, 긴 텍스트 등을 저장할 때 사용될 수 있습니다.
     *
     * @Column
     * @Column 어노테이션은 필드와 테이블 컬럼을 매핑하는 데 사용됩니다.
     * @Column을 사용하면 컬럼의 이름, 길이, 유니크 여부, nullable 여부 등 다양한 속성을 설정할 수 있습니다.
     *
     * bio 필드를 데이터베이스의 TEXT 타입 컬럼에 매핑하고 싶다면, 아래 같이
     *
     * 구글 선생 왈,
     * @Lob과 @Column 어노테이션은 함께 사용될 수 있습니다.
     * @Lob은 대용량 객체(Large Object)를 저장할 때 사용되는 어노테이션이고,
     * @Column은 필드와 테이블의 컬럼을 매핑할 때 사용되는 어노테이션입니다.
     * 두 어노테이션을 함께 사용하면, 특정 필드를 대용량 객체로 매핑하면서,
     * 동시에 데이터베이스 컬럼의 상세 설정을 지정할 수 있습니다
     * */

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")        // 자기소개가 길어질 수 있어 CLOB 매핑
    private String bio;                                         // 자기소개

    @Column(length = 255)
    private String website;                                     // URL 저장(검증은 DTO에서)

    private LocalDate birthdate;                                // 날짜만 필요(시간대 필요 없음)

    // 만든 이유 : 생성 시 User와 프로필 속성을 함께 세팅
    public Profile(User user,
                   String displayName,
                   String bio,
                   String website,
                   LocalDate birthdate
    ) {
        this.user = user;
        this.displayName = displayName;
        this.bio = bio;
        this.website = website;
        this.birthdate = birthdate;
    }

    // 만든 이유 : 변경 로직 캡슐화(Setter 대신)
    public void update(String displayName,
                       String bio,
                       String website,
                       LocalDate birthdate
    ) {
        this.displayName = displayName;
        this.bio = bio;
        this.website = website;
        this.birthdate = birthdate;
    }
}
