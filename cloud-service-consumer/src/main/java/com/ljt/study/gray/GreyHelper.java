package com.ljt.study.gray;

/**
 * @author LiJingTang
 * @date 2021-05-29 18:01
 */
class GreyHelper {

    public static final String VERSION = "grey-version";
    public static final String USER_ID = "user-id";

    private GreyHelper() {}

    private static final ThreadLocal<GreyDTO> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void set(GreyDTO dto) {
        THREAD_LOCAL.set(dto);
    }

    public static GreyDTO get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
