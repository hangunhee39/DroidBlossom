package site.timecapsulearchive.core.domain.member.data.dto;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import site.timecapsulearchive.core.domain.member.entity.MemberTemporary;
import site.timecapsulearchive.core.domain.member.entity.SocialType;
import site.timecapsulearchive.core.global.util.nickname.MakeRandomNickNameUtil;

public record SignUpRequestDto(
    String authId,
    String email,
    String profileUrl,
    SocialType socialType
) {

    public MemberTemporary toMemberTemporary() {
        return MemberTemporary.builder()
            .authId(authId)
            .nickname(MakeRandomNickNameUtil.makeRandomNickName())
            .email(email)
            .profileUrl(profileUrl)
            .socialType(socialType)
            .tag(NanoIdUtils.randomNanoId())
            .build();
    }

}
