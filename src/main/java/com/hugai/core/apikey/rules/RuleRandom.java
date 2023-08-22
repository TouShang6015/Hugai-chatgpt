package com.hugai.core.apikey.rules;

import com.hugai.core.apikey.OneKeyRule;
import com.hugai.core.apikey.RuleImplCommon;

import java.util.Random;

/**
 * 随机
 *
 * @author WuHao
 * @since 2023/5/31 14:43
 */
public class RuleRandom extends RuleImplCommon implements OneKeyRule {

    public RuleRandom(Long userId) {
        super(userId);
    }

    @Override
    public void execute() {
        int size = super.keys.size();
        Random random = new Random();
        random.setSeed(size);
        this.key = super.keys.get(random.nextInt(size));
    }

}
