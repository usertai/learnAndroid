package tv.yuyin.view.proxy;

public interface IVoiceListener {
    public static final int TYPE_NORMAL = ZAgreement.ATTR_RESULT_NORMAL;
    public static final int TYPE_ERROR = ZAgreement.ATTR_RESULT_ERROR;
    public static final int TYPE_CANCEL = ZAgreement.ATTR_RESULT_NOTHING;

    /**
     * voice begin
     */
    public abstract void onStart();

    /**
     * voice volume changed
     * 
     * @param vol
     *            volume
     */
    public abstract void onVoice(int vol);

    /**
     * go into recognize state
     */
    public abstract void onRecognizing();

    /**
     * will be call when user words are identified
     * 
     * @param code
     *            result type, error or normal, <br>
     *            or nothing just cancel and stop voice animation if exist
     * @param msg
     *            saying content, raw text
     * @return if you handle this msg
     */
    public abstract boolean onEnd(int code, String msg);

    // /**
    // * call back after Regist
    // * @param info regist result info from Yudian
    // */
    // public abstract void onRegist(String info);
    //
    // /**
    // * call back after unRegist
    // * @param info unRegist result info from Yudian
    // */
    // public abstract void onUnRegist(String info);
}
