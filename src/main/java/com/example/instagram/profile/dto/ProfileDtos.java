package com.example.instagram.profile.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProfileDtos {

    /**
     * Upsert는 데이터베이스에서 "Update 또는 Insert"의 약자로,
     * 특정 키 값을 기준으로 데이터가 이미 존재하면 해당 데이터를 업데이트하고,
     * 존재하지 않으면 새로운 데이터를 삽입하는 연산을 의미합니다.
     * 즉, 데이터베이스에 데이터를 추가하거나 수정할 때, 중복된 데이터를 처리하는 효율적인 방법입니다.
     *
     * 자세한 설명
     * Update (수정) : 이미 존재하는 데이터의 내용을 변경합니다.
     * 예를 들어, 특정 사용자의 이메일 주소가 변경되었을 때, 해당 사용자의 정보를 업데이트합니다.
     *
     * Insert (삽입) : 데이터베이스에 새로운 데이터를 추가합니다.
     * 예를 들어, 새로운 사용자가 가입했을 때, 해당 사용자의 정보를 데이터베이스에 추가합니다.
     *
     * Upsert (Update or Insert) : 위 두 연산을 합쳐서,
     * 특정 키 값을 기준으로 이미 존재하는 데이터는 업데이트하고, 존재하지 않으면 삽입하는 연산을 수행합니다.
     * */

//    public static class UpsertRequest {
//        @Size(max = 40)
//        private String displayName;
//
//        @Size(max = 500)
//        private String bio;
//
//        @Size(max = 200)
//        private String website;
//
//        @PastOrPresent
//        private LocalDate birthdate;
//    }
//
//    public static class ProfileResponse {
//        private Long userId;
//        private String displayName;
//        private String bio;
//        private String website;
//        private LocalDate birthdate;
//    }
}
