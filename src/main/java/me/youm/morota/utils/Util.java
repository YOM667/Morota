package me.youm.morota.utils;

import java.util.function.Consumer;

/**
 * @author YouM
 * Created on 2024/1/29
 */
public class Util {
    public static <OBJ> Result<OBJ> runCatching(CatchHandler<OBJ> catchHandler) {
        try {
            OBJ object = catchHandler.handler();
            return new Result.Success<>(object);
        } catch (Throwable throwable) {
            return new Result.Failure<>(throwable);
        }
    }

    public static void repeat(int count, Consumer<Integer> consumer) {
        for (int index = 0; index <= count; index++) {
            consumer.accept(index);
        }
    }

}
