package es.refil.presentation.codeQR

import android.content.Context
import android.view.WindowManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.data.Resource
import es.refil.data.network.qrCode.QrRepository
import es.refil.presentation.auth.login.LoginStateData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val qrRepository: QrRepository
) : ViewModel() {


    val currentUser: FirebaseUser?
        get() = qrRepository.currentUser

    val qrState: MutableState<QrStateData> = mutableStateOf(QrStateData())

    private val _qrFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val qrFlow: StateFlow<Resource<FirebaseUser>?> = _qrFlow


    suspend fun createQrCode(uuid: String) {


    }
}