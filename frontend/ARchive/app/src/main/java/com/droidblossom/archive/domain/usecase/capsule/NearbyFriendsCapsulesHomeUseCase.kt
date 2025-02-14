package com.droidblossom.archive.domain.usecase.capsule

import android.util.Log
import com.droidblossom.archive.domain.repository.CapsuleRepository
import com.droidblossom.archive.util.onException
import com.droidblossom.archive.util.onFail
import com.droidblossom.archive.util.onSuccess
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NearbyFriendsCapsulesHomeUseCase @Inject constructor(
    private val repository: CapsuleRepository
) {

    suspend operator fun invoke(latitude: Double, longitude: Double, distance: Double) =
        flow {
            try {
                emit(repository.nearbyFriendsCapsulesHome(latitude, longitude, distance).onSuccess {
                    Log.d("성패", "$it")
                }.onFail {
                    Log.d("실패", "$it")
                }.onException {
                    Log.d("실패", "$it")
                    throw Exception(it)
                })
            } catch (e: Exception) {
                Log.d("예외확인", "$e")
                e.printStackTrace()
            }
        }

}