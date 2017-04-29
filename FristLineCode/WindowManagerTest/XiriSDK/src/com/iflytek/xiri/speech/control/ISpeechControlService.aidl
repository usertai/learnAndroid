package com.iflytek.xiri.speech.control; 
import com.iflytek.xiri.speech.control.IControlCallback;

interface ISpeechControlService {
  /** 跟长虹讨论下把包名改成com.iflytek.xiri开头的，后面方面其他厂商使用
    *有语音输入开始识别时使用，用于获取全程语音状态
    */
    void startSpeech();
  
   void registerCallBack(IControlCallback cb);
    /**
    * 讯飞语点ASR完成识别将语义json传输给voicecontrol接口
    * @param text 识别文本数据
    * @param cb   回调接口
    * @return
    */
    void startControl(String text);

   
}