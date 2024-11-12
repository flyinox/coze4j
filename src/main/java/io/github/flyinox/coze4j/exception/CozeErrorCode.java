package io.github.flyinox.coze4j.exception;

public enum CozeErrorCode {
    // 客户端错误
    UNAUTHORIZED(401, "未授权"),
    NOT_FOUND(404, "资源未找到"),
    BAD_REQUEST(400, "请求错误"),

    // 请求参数错误 (4000-4099)
    INVALID_REQUEST_PARAM(4000, "请求参数错误"),
    INVALID_CHAT(4001, "无效的对话"),
    INVALID_CONVERSATION(4002, "无效的会话"),
    METADATA_LIMIT_EXCEEDED(4003, "meta data超过限制"),
    ADDITIONAL_MESSAGES_LIMIT_EXCEEDED(4004, "additional messages超过限制"),
    INVALID_MESSAGE(4005, "无效的消息"),
    INVALID_BOT(4006, "无效的Bot"),
    STREAM_MODE_ERROR(4007, "流模式设置错误"),
    USER_LIMIT_EXCEEDED(4008, "用户限制"),
    SYSTEM_REQUEST_LIMIT(4009, "系统请求上限"),
    TOKEN_LIMIT_EXCEEDED(4010, "prompt token数量超过模型上限"),
    INSUFFICIENT_BALANCE(4011, "Coze Token余额不足"),
    INVALID_MODEL(4012, "无效模型"),
    MODEL_RATE_LIMIT(4013, "模型限流"),
    QUESTION_CANNOT_ANSWER(4014, "问题无法回答"),
    BOT_NOT_PUBLISHED(4015, "bot未发布到API"),
    CHAT_ALREADY_RUNNING(4016, "当前会话已有chat在运行"),
    VOLCANO_INSUFFICIENT_BALANCE(4019, "火山Bot调用按量余额不足"),
    VOLCANO_RPM_EXCEEDED(4020, "火山Bot调用超出RPM峰值"),

    // 认证错误 (4100-4199)
    INVALID_AUTHENTICATION(4100, "身份验证无效"),
    PERMISSION_DENIED(4101, "没有权限访问该资源"),
    UNUSUAL_ACTIVITY(4102, "检测到异常活动"),

    // 资源错误 (4200-4299)
    RESOURCE_NOT_FOUND(4200, "资源未找到"),

    // 文件操作错误 (4300-4399)
    EMPTY_FILE(4300, "上传文件为空"),
    TOO_MANY_FILES(4301, "文件上传超过限制"),
    FILE_SIZE_EXCEEDED(4302, "文件大小超过限制"),
    UNSUPPORTED_FILE_TYPE(4303, "不支持的文件类型"),
    INVALID_FILE(4304, "无效的文件"),

    // 服务器错误 (5000-5999)
    INTERNAL_SERVER_ERROR(5000, "服务器内部错误"),

    // 知识库相关错误
    KNOWLEDGE_BASE_PERMISSION_DENIED(708232003, "知识库权限不足"),
    KNOWLEDGE_BASE_SERVER_ERROR(708230702, "知识库服务器错误"),
    KNOWLEDGE_BASE_PARAM_ERROR(708232001, "知识库参数错误"),
    PAT_ERROR(700012006, "个人访问令牌错误"),
    API_RATE_LIMIT(710005002, "API请求频率超限"),

    // 其他错误
    OTHER_ERROR(9999, "未知错误");

    private final int code;
    private final String message;

    CozeErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static CozeErrorCode fromCode(int code) {
        for (CozeErrorCode errorCode : CozeErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        if(code >= 500 && code < 600) {
            return INTERNAL_SERVER_ERROR;
        }
        if(code >= 400 && code < 500) {
            return BAD_REQUEST;
        }
        return OTHER_ERROR;
    }
} 