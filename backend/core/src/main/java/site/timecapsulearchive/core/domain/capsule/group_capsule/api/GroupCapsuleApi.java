package site.timecapsulearchive.core.domain.capsule.group_capsule.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import site.timecapsulearchive.core.domain.capsule.group_capsule.data.reqeust.GroupCapsuleCreateRequest;
import site.timecapsulearchive.core.domain.capsule.group_capsule.data.reqeust.GroupCapsuleUpdateRequest;
import site.timecapsulearchive.core.domain.capsule.group_capsule.data.response.GroupCapsuleDetailResponse;
import site.timecapsulearchive.core.domain.capsule.group_capsule.data.response.GroupCapsulePageResponse;
import site.timecapsulearchive.core.domain.capsule.group_capsule.data.response.GroupCapsuleSummaryResponse;
import site.timecapsulearchive.core.domain.capsule.group_capsule.data.response.MyGroupCapsuleSliceResponse;
import site.timecapsulearchive.core.global.common.response.ApiSpec;
import site.timecapsulearchive.core.global.error.ErrorResponse;


@Validated
public interface GroupCapsuleApi {

    @Operation(
        summary = "그룹 캡슐 생성",
        description = "그룹원들이 볼 수 있는 그룹 캡슐을 생성한다.",
        security = {@SecurityRequirement(name = "user_token")},
        tags = {"group capsule"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "처리 완료"
        ),
        @ApiResponse(
            responseCode = "404",
            description = """
                요청이 잘못되어 발생하는 오류이다.
                <ul>
                <li>회원을 찾을 수 없는 경우 발생한다.</li>
                <li>캡슐 스킨을 찾을 수 없는 경우 발생한다.</li>
                <li>그룹을 찾을 수 없는 경우 발생한다.</li>
                </ul>
                """,
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    ResponseEntity<ApiSpec<String>> createGroupCapsule(
        Long memberId,

        @Parameter(in = ParameterIn.PATH, description = "생성할 그룹 아이디", required = true)
        Long groupId,

        @Valid GroupCapsuleCreateRequest request
    );

    @Operation(
        summary = "그룹 캡슐 상세 조회",
        description = "그룹원만 볼 수 있는 그룹 캡슐 내용을 상세 조회한다.",
        security = {@SecurityRequirement(name = "user_token")},
        tags = {"group capsule"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "처리 완료"
        )
    })
    ResponseEntity<ApiSpec<GroupCapsuleDetailResponse>> getGroupCapsuleDetailByCapsuleId(
        Long memberId,

        @Parameter(in = ParameterIn.PATH, description = "조회할 캡슐 아이디", required = true)
        Long capsuleId
    );

    @Operation(
        summary = "그룹 캡슐 요약 조회",
        description = "그룹원만 볼 수 있는 그룹 캡슐 내용을 요약 조회한다.",
        security = {@SecurityRequirement(name = "user_token")},
        tags = {"group capsule"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "처리 완료"
        )
    })
    ResponseEntity<ApiSpec<GroupCapsuleSummaryResponse>> getGroupCapsuleSummaryByCapsuleId(
        Long memberId,

        @Parameter(in = ParameterIn.PATH, description = "조회할 캡슐 아이디", required = true)
        Long capsuleId
    );

    @Operation(
        summary = "그룹 캡슐 목록 조회",
        description = "그룹원만 볼 수 있는 그룹 캡슐 목록을 조회한다.",
        security = {@SecurityRequirement(name = "user_token")},
        tags = {"group capsule"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "처리 완료"
        )
    })
    @GetMapping(
        value = "/groups/{group_id}/capsules",
        produces = {"application/json"}
    )
    ResponseEntity<GroupCapsulePageResponse> getGroupCapsules(
        @Parameter(in = ParameterIn.PATH, description = "그룹 아이디", required = true, schema = @Schema())
        @PathVariable("group_id") Long groupId,

        @Parameter(in = ParameterIn.QUERY, description = "페이지 크기", required = true, schema = @Schema())
        @NotNull @Valid @RequestParam(value = "size") Long size,

        @Parameter(in = ParameterIn.QUERY, description = "마지막 캡슐 아이디", required = true, schema = @Schema())
        @NotNull @Valid @RequestParam(value = "capsule_id") Long capsuleId
    );

    @Operation(
        summary = "사용자가 만든 그룹 캡슐 목록 조회",
        description = "사용자가 만든 그룹 캡슐 목록을 조회한다.",
        security = {@SecurityRequirement(name = "user_token")},
        tags = {"group capsule"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "처리 완료"
        )
    })
    ResponseEntity<ApiSpec<MyGroupCapsuleSliceResponse>> getMyGroupCapsules(
        Long memberId,

        @Parameter(in = ParameterIn.QUERY, description = "페이지 크기", required = true, schema = @Schema())
        @Range(min = 0, max = 50)
        int size,

        @Parameter(in = ParameterIn.QUERY, description = "마지막 캡슐 생성 시간", required = true, schema = @Schema())
        ZonedDateTime createAt
    );

    @Operation(
        summary = "그룹 캡슐 24시간 이내 수정",
        description = "사용자가 생성한 그룹 캡슐의 생성 시간이 24시간 이내라면 수정한다.",
        security = {@SecurityRequirement(name = "user_token")},
        tags = {"group capsule"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "처리 완료"
        )
    })
    @PatchMapping(
        value = "/groups/{group_id}/capsules/{capsule_id}",
        consumes = {"multipart/form-data"}
    )
    ResponseEntity<GroupCapsuleSummaryResponse> updateGroupCapsuleByIdAndGroupId(
        @Parameter(in = ParameterIn.PATH, description = "수정할 캡슐의 그룹 아이디", required = true, schema = @Schema())
        @PathVariable("group_id") Long groupId,

        @Parameter(in = ParameterIn.PATH, description = "수정할 그룹 캡슐 아이디", required = true, schema = @Schema())
        @PathVariable("capsule_id") Long capsuleId,

        @ModelAttribute GroupCapsuleUpdateRequest request
    );

}
