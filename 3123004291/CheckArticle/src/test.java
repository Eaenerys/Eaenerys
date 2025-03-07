import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlagiarismCheckerTest {

    @Test
    void testReadFileNotFound() {
        // 测试文件不存在时的异常处理
        PlagiarismChecker checker = new PlagiarismChecker();
        String invalidPath = "invalid_path.txt";
        assertThrows(IOException.class, () -> checker.readFile(invalidPath), "文件不存在时应抛出 IOException");
    }

    @Test
    void testGetWordFrequencyEmptyText() {
        PlagiarismChecker checker = new PlagiarismChecker();
        String emptyText = "";
        Map<String, Integer> freqMap = checker.getWordFrequency(emptyText);
        assertTrue(freqMap.isEmpty(), "空文本的词频统计结果应为空");
    }
    @Test
    void testCalculateCosineSimilarity() {
        // 测试余弦相似度计算功能
        PlagiarismChecker checker = new PlagiarismChecker();
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("今天", 2);
        map1.put("天气", 1);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("今天", 1);
        map2.put("天气", 2);

        double similarity = checker.calculateCosineSimilarity(map1, map2);
        assertEquals(0.8, similarity, 0.01, "余弦相似度计算错误");
    }

    @Test
    void testCalculateCosineSimilarityEmptyMaps() {
        // 测试空词频映射的余弦相似度计算
        PlagiarismChecker checker = new PlagiarismChecker();
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();

        double similarity = checker.calculateCosineSimilarity(map1, map2);
        assertEquals(0.0, similarity, 0.01, "空词频映射的余弦相似度应为 0");
    }

    @Test
    void testCalculateNorm() {
        // 测试向量模长计算功能
        PlagiarismChecker checker = new PlagiarismChecker();
        List<Integer> vector = List.of(3, 4); // 3^2 + 4^2 = 25, sqrt(25) = 5
        double norm = checker.calculateNorm(vector);
        assertEquals(5.0, norm, 0.01, "向量模长计算错误");
    }

    @Test
    void testCalculateNormEmptyVector() {
        // 测试空向量的模长计算
        PlagiarismChecker checker = new PlagiarismChecker();
        List<Integer> emptyVector = List.of();
        double norm = checker.calculateNorm(emptyVector);
        assertEquals(0.0, norm, 0.01, "空向量的模长应为 0");
    }

    @Test
    void testWriteResult() throws IOException {
        // 测试结果写入功能
        PlagiarismChecker checker = new PlagiarismChecker();
        String outputPath = "test_output.txt";
        double similarity = 0.75;

        checker.writeResult(outputPath, similarity);

        // 验证写入的文件内容
        String content = checker.readFile(outputPath);
        assertEquals("0.75", content.trim(), "写入的文件内容不正确");
    }

    @Test
    void testWriteResultInvalidPath() {
        PlagiarismChecker checker = new PlagiarismChecker();
        String invalidPath = "/invalid/path/output.txt";
        double similarity = 0.75;

        assertThrows(IOException.class, () -> checker.writeResult(invalidPath, similarity), "文件路径无效时应抛出 IOException");
    }
}