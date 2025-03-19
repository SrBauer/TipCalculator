import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TipCalculatorViewModel : ViewModel() {
    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _customTipPercent = MutableStateFlow(15)
    val customTipPercent: StateFlow<Int> = _customTipPercent

    fun updateAmount(newAmount: String) {
        _amount.value = newAmount
    }

    fun updateCustomTipPercent(newPercent: Int) {
        _customTipPercent.value = newPercent
    }

    fun calculateTip(amount: Double, percent: Int): Double {
        return amount * (percent / 100.0)
    }
}
