package elmeniawy.eslam.nutrisport.shared

/**
 * Constants
 *
 * Created by Eslam El-Meniawy on 10-Aug-2025 at 4:46 PM.
 */
object Constants {
    val WEB_CLIENT_ID = BuildKonfig.WEB_CLIENT_ID

    const val PAYPAL_CLIENT_ID = ""
    const val PAYPAL_SECRET_ID = ""

    const val PAYPAL_AUTH_KEY = "$PAYPAL_CLIENT_ID:$PAYPAL_SECRET_ID"

    const val PAYPAL_AUTH_ENDPOINT = "https://api-m.sandbox.paypal.com/v1/oauth2/token"
    const val PAYPAL_CHECKOUT_ENDPOINT = "https://api-m.sandbox.paypal.com/v2/checkout/orders"

    const val RETURN_URL = "com.stevdza.san.nutrisport://paypalpay?success=true"
    const val CANCEL_URL = "com.stevdza.san.nutrisport://paypalpay?cancel=true"

    const val MAX_QUANTITY = 10
    const val MIN_QUANTITY = 1
}