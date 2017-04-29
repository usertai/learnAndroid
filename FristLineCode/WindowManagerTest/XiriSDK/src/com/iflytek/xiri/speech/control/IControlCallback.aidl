package com.iflytek.xiri.speech.control;

//IControlCallback作为回调接口
interface IControlCallback {
    /**
    * @param feedback 播报内容
    * @param feedbackflag 播报类型    0、英文  1、执行     2、静默      3、聊天对话     4、错误信息
    * @return
    */
    void onFeedback(boolean result,String feedback,int feedbackflag);
}