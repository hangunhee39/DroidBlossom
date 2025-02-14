package site.timecapsulearchive.core.domain.member.data.dto;

import lombok.Builder;

@Builder
public record MemberDetailDto(
    String nickname,
    String profileUrl,
    String tag,
    Long friendCount,
    Long groupCount
) {

}
