package es.refil.presentation.codeQR

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.lifecycle.HiltViewModel
import es.refil.data.Resource
import es.refil.data.network.qrCode.QrRepository
import es.refil.presentation.auth.login.LoginStateData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val qrRepository: QrRepository
) : ViewModel() {

    val currentUser: FirebaseUser?
        get() = qrRepository.currentUser

    private val _qrFlow = MutableStateFlow<Resource<BitMatrix>?>(null)
    val qrFlow: StateFlow<Resource<BitMatrix>?> = _qrFlow


//    suspend fun createQrCode(uuid: String) {
//
//        viewModelScope.launch {
//
//            val result = qrRepository.generateQrCode(uuid)
//            _qrFlow.value = result
//
//        }
//    }


}