package com.droidblossom.archive.data.repository

import com.droidblossom.archive.data.dto.ResponseBody
import com.droidblossom.archive.data.dto.capsule_skin.response.CapsuleSkinsPageResponseDto
import com.droidblossom.archive.data.dto.common.PagingRequestDto
import com.droidblossom.archive.data.dto.common.toModel
import com.droidblossom.archive.data.dto.friend.request.FriendAcceptRequestDto
import com.droidblossom.archive.data.dto.friend.request.FriendReqRequestDto
import com.droidblossom.archive.data.dto.friend.request.FriendsReqRequestDto
import com.droidblossom.archive.data.dto.friend.request.FriendsSearchPhoneRequestDto
import com.droidblossom.archive.data.dto.friend.request.FriendsSearchRequestDto
import com.droidblossom.archive.data.dto.friend.response.FriendReqStatusResponseDto
import com.droidblossom.archive.data.dto.friend.response.FriendsPageResponseDto
import com.droidblossom.archive.data.dto.friend.response.FriendsSearchPhoneResponseDto
import com.droidblossom.archive.data.dto.friend.response.FriendsSearchResponseDto
import com.droidblossom.archive.data.source.remote.api.FriendService
import com.droidblossom.archive.domain.model.friend.FriendReqStatusResponse
import com.droidblossom.archive.domain.model.friend.FriendsPage
import com.droidblossom.archive.domain.model.friend.FriendsSearchPhoneRequest
import com.droidblossom.archive.domain.model.friend.FriendsSearchPhoneResponse
import com.droidblossom.archive.domain.model.friend.FriendsSearchResponse
import com.droidblossom.archive.domain.repository.FriendRepository
import com.droidblossom.archive.util.RetrofitResult
import com.droidblossom.archive.util.apiHandler
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val api : FriendService
) : FriendRepository {
    override suspend fun postFriendsRequest(request: FriendReqRequestDto): RetrofitResult<FriendReqStatusResponse> {
        return apiHandler({ api.postFriendsRequestApi(request.friendId) }) { response: ResponseBody<FriendReqStatusResponseDto> -> response.result.toModel() }
    }

    override suspend fun postFriendsListRequest(request: FriendsReqRequestDto): RetrofitResult<String> {
        return apiHandler({api.postFriendListRequestsPageApi(request)}) { response : ResponseBody<String> ->response.result.toModel()}
    }

    override suspend fun postFriendsAcceptRequest(request: FriendAcceptRequestDto): RetrofitResult<String> {
        return apiHandler({ api.postFriendsAcceptRequestApi(request.friendId) }) {response: ResponseBody<String> -> response.result.toModel()}
    }

    override suspend fun postFriendsSearch(request: FriendsSearchRequestDto): RetrofitResult<FriendsSearchResponse> {
        return apiHandler({ api.postFriendsSearchApi(request.friendTag) }) {response: ResponseBody<FriendsSearchResponseDto> -> response.result.toModel()}
    }

    override suspend fun postFriendsSearchPhone(request: FriendsSearchPhoneRequestDto): RetrofitResult<FriendsSearchPhoneResponse> {
        return apiHandler({ api.postFriendsSearchPhoneApi(request) }) {response: ResponseBody<FriendsSearchPhoneResponseDto> -> response.result.toModel()}
    }

    override suspend fun getFriendsPage(request: PagingRequestDto): RetrofitResult<FriendsPage> {
        return apiHandler({api.getFriendsPageApi(request.size, request.createdAt)}) {response: ResponseBody<FriendsPageResponseDto> -> response.result.toModel()}
    }

    override suspend fun getFriendsRequestsPage(request: PagingRequestDto): RetrofitResult<FriendsPage> {
        return apiHandler({api.getFriendsRequestsPageApi(request.size, request.createdAt)}) {response: ResponseBody<FriendsPageResponseDto> -> response.result.toModel()}
    }

    override suspend fun deleteFriend(friendId: Long): RetrofitResult<String> {
        return apiHandler({api.deleteFriendApi(friendId)}) {response: ResponseBody<String> -> response.result.toModel()}
    }

    override suspend fun deleteFriendDeny(friendId: Long): RetrofitResult<String> {
        return apiHandler({api.deleteFriendDenyRequestApi(friendId)}) {response: ResponseBody<String> -> response.result.toModel()}
    }
}