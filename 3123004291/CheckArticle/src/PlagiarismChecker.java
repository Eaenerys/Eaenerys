import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class PlagiarismChecker {
    // 读取文件内容
    static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    //分词并统计词频
//    static Map<String, Integer> getWordFrequency(String text) {
//        // 简单分词：按空格和非字母数字字符分割
//        Map<String, Integer> freqMap = new HashMap<>();
//        for (String word : text.split("[\\s,.，。]+")) { // 使用更适合中文的分词方式
//            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
//        }
//        return freqMap;
//    }
    static Map<String, Integer> getWordFrequency(String text) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : text.split("[\\s,.，。]+")) {
            if (!word.isEmpty()) { // 过滤空字符串
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }
        return freqMap;
    }


    // 计算余弦相似度
    static double calculateCosineSimilarity(
            Map<String, Integer> map1,
            Map<String, Integer> map2) {

        // 获取所有唯一词语
        Set<String> allWords = new HashSet<>(map1.keySet());
        allWords.addAll(map2.keySet());

        // 创建特征向量
        List<Integer> vec1 = new ArrayList<>();
        List<Integer> vec2 = new ArrayList<>();
        for (String word : allWords) {
            vec1.add(map1.getOrDefault(word, 0));
            vec2.add(map2.getOrDefault(word, 0));
        }

        // 计算点积
        double dotProduct = 0;
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
        }

        // 计算模长
        double norm1 = calculateNorm(vec1);
        double norm2 = calculateNorm(vec2);

        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        return dotProduct / (norm1 * norm2);
    }

    // 计算向量模长
    static double calculateNorm(List<Integer> vector) {
        double sum = 0;
        for (int num : vector) {
            sum += Math.pow(num, 2);
        }
        return Math.sqrt(sum);
    }

    // 写入结果文件
    static void writeResult(String outputPath, double similarity) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(String.format("%.2f", similarity));
        }
    }



}

