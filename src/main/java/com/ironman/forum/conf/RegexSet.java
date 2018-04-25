package com.ironman.forum.conf;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * 正则集合类
 */
public class RegexSet<E> extends HashSet<E> {

    @Override
    public boolean contains(Object key) {
        if (super.contains(key)) {
            return true;
        }
        for (Object k : this) {
            Pattern pattern = Pattern.compile(k.toString());
            if (pattern.matcher(key.toString()).matches()) {
                return true;
            }
        }
        return false;
    }
}
