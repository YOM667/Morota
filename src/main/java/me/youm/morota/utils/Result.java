package me.youm.morota.utils;

import java.util.function.Consumer;

/**
 * @author YouM
 * Created on 2024/1/29
 */
public interface Result<OBJ>{
    OBJ get();
    Throwable getThrowable();
    int getState();
    int SUCCESS = 0;
    int FAILED = 1;
    default Result<OBJ> onSuccess(Consumer<? super OBJ> consumer){
        if(getState() == SUCCESS) consumer.accept(get());
        return this;
    }
    default Result<OBJ> onFailed(Consumer<? super Throwable> consumer){
        if(getState() == FAILED)consumer.accept(getThrowable());
        return this;
    }
    record Success<OBJ>(OBJ object) implements Result<OBJ>{
        public OBJ get(){
            return object;
        }
        @Override
        public Throwable getThrowable() {
            return null;
        }

        @Override
        public int getState() {
            return SUCCESS;
        }
    }

    record Failure<OBJ>(Throwable throwable) implements Result<OBJ> {
        public OBJ get() {
            return null;
        }

        @Override
        public Throwable getThrowable() {
            return throwable;
        }

        @Override
        public int getState() {
            return FAILED;
        }
    }

}