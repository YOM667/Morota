package me.youm.morota.utils;

/**
 * @author YouM
 * Created on 2024/1/29
 */

@FunctionalInterface
public interface CatchHandler<T>{
    T handler() throws Throwable;
}

