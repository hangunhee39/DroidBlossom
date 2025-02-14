package site.timecapsulearchive.core.domain.history.data.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "히스토리 요약 정보")
public record HistorySummaryResponse(

    @Schema(description = "제목")
    String title,

    @Schema(description = "히스토리 썸네일 url")
    String thumbnailUrl
) {

}