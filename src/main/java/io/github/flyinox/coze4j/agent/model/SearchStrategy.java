package io.github.flyinox.coze4j.agent.model;

/**
 * 知识库搜索策略
 */
public enum SearchStrategy {
    /**
     * 语义搜索：像人类一样去理解词与词，句与句之间的关系
     */
    SEMANTIC(0),
    
    /**
     * 混合搜索：结合全文检索和语义检索的优势，并对结果进行综合排序召回相关的内容片段
     */
    HYBRID(1),
    
    /**
     * 全文搜索：基于关键词进行全文检索
     */
    FULL_TEXT(20);

    private final int value;

    SearchStrategy(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SearchStrategy fromValue(int value) {
        for (SearchStrategy strategy : SearchStrategy.values()) {
            if (strategy.value == value) {
                return strategy;
            }
        }
        return SEMANTIC; // 默认返回语义搜索
    }
} 