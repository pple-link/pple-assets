package link.pple.assets.infrastructure.alarm

import link.pple.assets.infrastructure.alarm.sms.SmsClient
import link.pple.assets.infrastructure.alarm.sms.SmsMessage
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class AlarmCenter(
    private val smsClient: SmsClient
) {

    @Async
    fun sendSmsAsync(smsMessage: SmsMessage) {
        sendSms(smsMessage)
        listOf("")
    }

    fun sendSms(smsMessage: SmsMessage) {
        smsClient.sendOne(smsMessage)
    }
}
