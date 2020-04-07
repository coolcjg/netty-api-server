package ch9.service.dao;

import ch9.core.KeyMaker;
import redis.clients.jedis.util.MurmurHash;

public class TokenKey implements KeyMaker {
    static final int SEED_MURMURHASH = 0x1234ABCD;

    private String email;
    private long issueDate;

    /**
     * �궎 硫붿씠而� �겢�옒�뒪瑜� �쐞�븳 �깮�꽦�옄.
     * 
     * @param email
     *            �궎 �깮�꽦�쓣 �쐞�븳 �씤�뜳�뒪 媛�
     * @param issueDate
     */
    public TokenKey(String email, long issueDate) {
        this.email = email;
        this.issueDate = issueDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.redisbook.ch7.redislogger.KeyMaker#getKey()
     */
    @Override
    public String getKey() {
        String source = email + String.valueOf(issueDate);

        return Long.toString(MurmurHash.hash64A(source.getBytes(), SEED_MURMURHASH), 16);
    }
}
