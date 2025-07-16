import java.nio.charset.StandardCharsets;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterEmail {
    private static  BloomFilterEmail instance;
    private BloomFilter<String> filter;

    private BloomFilterEmail() {
        filter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                1000,
                0.01
            );
    }

    public static BloomFilterEmail getInstance() {
        if (null == instance) {
            instance = new BloomFilterEmail();
        }
        
        return instance;
    }

    public boolean contains(String email) {
        return filter.mightContain(email);
    }

    public void add(String email) {
        filter.put(email);
    }
    
}