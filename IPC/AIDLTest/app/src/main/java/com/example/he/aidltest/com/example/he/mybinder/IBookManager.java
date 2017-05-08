package com.example.he.aidltest.com.example.he.mybinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.example.he.aidltest.Book;

import java.util.List;

/**
 * 自己实现一个Binder,
 * AIDL并不是实现Binder的必须品，
 * AIDL文件只是为我们提供了一种快速实现Binder的工具而已
 * Created by he on 2017/5/8.
 */

public interface IBookManager extends IInterface {
    static final String DESCRIPTOR = "com.example.he.aidltest.com.example.he.mybinder.IBookManager";//Binder的唯一标识符，一般为当前类名
    static final int TRASACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;//用于在onTransact中进行方法判断，aidi中声明了几个方法就要写几个

    public List<Book> getBookList() throws RemoteException;


    /**
     * 实现Stub类和Stub类中的Proxy代理
     */

    public class BookManagerImpl extends Binder implements IBookManager {


        public BookManagerImpl(){
            //连接接口
            this.attachInterface(this,DESCRIPTOR);
        }

        




        //Interface中声明的方法
        @Override
        public List<Book> getBookList() throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }
    }


}
