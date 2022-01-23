package link.pple.assets.infrastructure.alarm.sms

import net.nurigo.sdk.NurigoApp
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.MessageListRequest
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.stereotype.Service


/**
 * @Author Heli
 */
@Service
class SmsClient(
    private val nurigoAppProperties: NurigoAppProperties
) {

    private val messageService: DefaultMessageService by lazy {
        NurigoApp.initialize(
            nurigoAppProperties.apiKey,
            nurigoAppProperties.apiSecretKey,
            nurigoAppProperties.domain
        )
    }

    fun getMessageList() {
        val response = messageService.getMessageList(MessageListRequest())
        println(response)
    }

    fun sendOne(smsMessage: SmsMessage): SingleMessageSentResponse? {
        val message = Message(
            from = nurigoAppProperties.fromPhoneNumber,
            to = smsMessage.toPhoneNumber,
            text = smsMessage.text,
        )
        val singleMessageSendingRequest = SingleMessageSendingRequest(message)
//        return messageService.sendOne(singleMessageSendingRequest)
        return null
    }
}
