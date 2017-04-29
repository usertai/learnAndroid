package tv.yuyin.view.proxy;

public final class ZAgreement {
    protected static final int PROTOCAL_VERSION = 0x1000;
    protected static final String K_PROTOCAL_VERSION = "protocal_version_i";
    protected static final String K_TOKEN = "talk_token_i";

    protected static final String K_FROM_PACKAGE = "from_package_s";

    protected static final String PACKAGE_YUYIN = "tv.yuyin";
    protected static final String PACKAGE_XIRI = "com.iflytek.xiri";
    protected static final String ACTION_CONNECT = "tv.yuyin.voiceview.connect";

    protected static final String ACTION_REPORT_RESULT = "tv.yuyin.voiceview.report.result";
    protected static final String K_HAS_HANDLE = "has_handle_b";

    protected static final String ACTION_TTS_TEXT = "tv.yuyin.voiceview.tts.text";
    protected static final String K_TTS_TEXT = "tts_s";
    protected static final String K_TTS_TYPE = "tts_type_i";
    protected static final int V_TTS_TYPE_CH = 0x1001;
    protected static final int V_TTS_TYPE_EN = 0x1002;

    protected static final String ACTION_REGIST = "tv.yuyin.voiceview.regist";
    protected static final String K_REGIST_PARAM = "regist_param_s";
    protected static final String ACTION_UNREGIST = "tv.yuyin.voiceview.unregist";

    protected static final String K_OPERATION = "OPERATION_s";

    protected static final String V_OPERATION_onStart = "onStart";

    protected static final String V_OPERATION_onVoice = "onVoice";
    protected static final String K_ONVOICE_vol = "onVoice_vol_i";

    protected static final String V_OPERATION_onRecognizing = "onRecognizing";

    protected static final String V_OPERATION_onEnd = "onEnd";
    protected static final String K_ONEND_code = "onEnd_code_i";
    protected static final String K_ONEND_msg = "onEnd_msg_s";

    protected static final int ATTR_RESULT_NORMAL = 0x1001;
    protected static final int ATTR_RESULT_ERROR = 0x1002;
    protected static final int ATTR_RESULT_NOTHING = 0x1003;
}
